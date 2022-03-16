package it.sogei.mongohack.huskyme.sensorpusher.components.mongoconnector;

import com.google.gson.Gson;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import it.sogei.mongohack.huskyme.sensorpusher.components.mongoconnector.ifaces.IMongoConnector;
import it.sogei.mongohack.huskyme.sensorpusher.model.Animale;
import it.sogei.mongohack.huskyme.sensorpusher.model.Padrone;
import it.sogei.mongohack.huskyme.sensorpusher.model.Sensore;
import it.sogei.mongohack.huskyme.sensorpusher.util.GsonUtil;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MongoConnector implements IMongoConnector {
    private MongoClient mongoClient;

    private MongoDatabase database;

    private Gson gson;

    {
        this.mongoClient = MongoClients.create("mongodb+srv://bst:bst@demo.revhp.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");

        this.database = mongoClient.getDatabase("Beast");

        this.gson = GsonUtil.getUtil().getGson();
    }

    @Override
    public void aggiungiPadrone(Padrone p) {
        MongoCollection<Document> padroniCollection = this.database.getCollection("padroni");

        Document toIns = Document.parse(this.gson.toJson(p)).append("_id", new ObjectId());

        padroniCollection.insertOne(toIns);
    }

    @Override
    public List<Padrone> getPadroni() {
        MongoCollection<Document> padroniCollection = this.database.getCollection("padroni");
        FindIterable<Document> padroni = padroniCollection.find();

        return padroni.map(doc -> this.gson.fromJson(doc.toJson(), Padrone.class)).into(new LinkedList<>());
    }

    @Override
    public void aggiungiAnimale(Padrone p, Animale a) {
        MongoCollection<Document> padroniCollection = this.database.getCollection("padroni");
        MongoCollection<Document> animaliCollection = this.database.getCollection("animali");

        Document toIns = Document.parse(this.gson.toJson(a)).append("_id", new ObjectId());

        animaliCollection.insertOne(toIns);

        Bson search = Filters.eq("codPadrone", p.getCodPadrone());

        Bson toPush = Updates.push("animali", new Document().append("idAnimale", a.getIdAnimale()).append("nome", a.getNome()));

        padroniCollection.updateOne(search, toPush);
    }

    @Override
    public List<Animale> getAnimaleDi(Padrone p) {
       MongoCollection<Document> animaliCollection = this.database.getCollection("animali");

        Bson search = Filters.eq("codPadrone", p.getCodPadrone());

        FindIterable<Document> animali = animaliCollection.find(search);

        return animali.map(d -> this.gson.fromJson(d.toJson(), Animale.class)).into(new LinkedList<>());
    }

    @Override
    public void aggiungiMisurazione(Sensore misurazione) {
        MongoCollection<Document> sensoriCollection = this.database.getCollection("sensori");

        Document toIns = Document.parse(this.gson.toJson(misurazione)).append("_id",new ObjectId());

        sensoriCollection.insertOne(toIns);
    }

    public void close() {
        this.mongoClient.close();
    }
}
