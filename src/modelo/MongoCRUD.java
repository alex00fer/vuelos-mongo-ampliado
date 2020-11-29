package modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import org.bson.Document;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class MongoCRUD implements CRUDVentas {
	
	MongoConnection conn = new MongoConnection();
	
	@Override
	public Collection<Vuelo> getVuelosDisponibles() throws Exception {
		
		Collection<Vuelo> vuelos = new ArrayList<Vuelo>();
		
		Document filter = new Document("plazas_disponibles", new Document("$gt", 0));
		Document projection = new Document("vendidos", false).append("asientos_disponibles", false);
		
		MongoCollection<Document> vuelosColl = conn.getColeccion();
		FindIterable<Document> iterable = vuelosColl.find(filter).projection(projection);
		for (Document doc : iterable) {
			Vuelo vuelo = documentToVuelo(doc);
			vuelos.add(vuelo);
		}
		
		return vuelos;
	}
	
	@Override
	public Vuelo getVuelo(String codigo) throws Exception {

		Document filter = new Document("codigo", codigo);
		Document projection = new Document("vendidos", false).append("asientos_disponibles", false);
		
		MongoCollection<Document> vuelosColl = conn.getColeccion();
		Document doc = vuelosColl.find(filter).projection(projection).first();
		return  documentToVuelo(doc);
	}
	
	@Override
	public Venta obtenerVenta(String codigoVuelo, String codigoVenta) throws Exception {
		MongoCollection<Document> vuelosColl = conn.getColeccion();
		
		Document filter = new Document("codigo", codigoVuelo);
		Document projection = 
				new Document("vendidos", 
						new Document("$elemMatch", new Document("codigoVenta", codigoVenta)));
		
		Document v = vuelosColl.find(filter).projection(projection).first().getList("vendidos", Document.class).get(0);
		return documentToVenta(v);
	}
	
	@Override
	public String insertarVenta(String codigo, Venta venta) throws Exception {

		MongoCollection<Document> vuelosColl = conn.getColeccion();
		
		venta.setCodigoVenta(generarCodigoVenta());
		venta.setAsiento(sacarAsiento(codigo));
		// decrement plazas libres
		RestarPlaza(codigo);
		Document updateQuery = new Document("codigo", codigo);
		Document updateValues = 
				new Document("$push", 
						new Document("vendidos", ventaToDocument(venta)));
		
		vuelosColl.updateOne(updateQuery, updateValues);
		return venta.getCodigoVenta();
	}
	
	@Override
	public boolean eliminarVenta(String codigoVuelo, String codigoVenta, String dni) throws Exception {
		MongoCollection<Document> vuelosColl = conn.getColeccion();
		
		// obtener asiento y devolverlo
		Venta venta = obtenerVenta(codigoVuelo, codigoVenta);
		devolverAsiento(codigoVuelo, venta.getAsiento());
		// increment plazas libres
		SumarPlaza(codigoVuelo);
		// borrar venta
		Document updateQuery = new Document("codigo", codigoVuelo);
		Document updateValues = 
				new Document("$pull", 
						new Document("vendidos", new Document("codigoVenta", codigoVenta).append("dni", dni)));
		
		return vuelosColl.updateOne(updateQuery, updateValues).getModifiedCount() == 1;
	}
	
	@Override
	public boolean modificarVenta(String codVuelo, String codVenta, String dni, String mn, String ma, String md, String mp,
			String mt) throws Exception {
		
		MongoCollection<Document> vuelosColl = conn.getColeccion();
		
		Document updateQuery = new Document("codigo", codVuelo)
				.append("vendidos", 
						new Document("$elemMatch", 
								new Document("codigoVenta", codVenta)));
		Document updateValues = 
				new Document("$set", 
						new Document()
						.append("vendidos.$.nombre", mn)
						.append("vendidos.$.apellido", ma)
						.append("vendidos.$.dni", md)
						.append("vendidos.$.dniPagador", mp)
						.append("vendidos.$.tarjeta", mt)
						);
		
		return vuelosColl.updateOne(updateQuery, updateValues).getModifiedCount() == 1;
	}
	
	
	private void SumarPlaza(String codigo) {
		MongoCollection<Document> vuelosColl = conn.getColeccion();
		// suma 1 a plazas disponinles
		Document updateQuery = new Document("codigo", codigo);
		Document updateValues = 
				new Document("$inc", 
						new Document("plazas_disponibles", 1));
		
		vuelosColl.updateOne(updateQuery, updateValues);
	}
	
	private void RestarPlaza(String codigo) {
		MongoCollection<Document> vuelosColl = conn.getColeccion();
		// resta 1 a plazas disponinles
		Document updateQuery = new Document("codigo", codigo);
		Document updateValues = 
				new Document("$inc", 
						new Document("plazas_disponibles", -1));
		
		vuelosColl.updateOne(updateQuery, updateValues);
	}

	
	private int sacarAsiento(String codigo) {
		// obtener asiento
		MongoCollection<Document> vuelosColl = conn.getColeccion();
		List<Document> aggregation =new ArrayList<Document>();
		aggregation.add(new Document("$match", new Document("codigo", codigo)));
		aggregation.add(
				new Document("$project", 
						new Document("_id", 0).append("asiento", 
								new Document("$arrayElemAt", 
										Arrays.asList(new Object[] {"$asientos_disponibles", 0})))));
		AggregateIterable<Document> iterable = vuelosColl.aggregate(aggregation);
		
		
		int asiento = iterable.first().getInteger("asiento");
		
		// borrar asiento
		Document updateQuery = new Document("codigo", codigo);
		Document updateValues = 
				new Document("$pop", 
						new Document("asientos_disponibles", -1));
		
		vuelosColl.updateOne(updateQuery, updateValues);
		
		// devolver asiento
		return asiento;
	}
	
	private void devolverAsiento(String codigo, int asiento) {
		
		MongoCollection<Document> vuelosColl = conn.getColeccion();
		// agregar asiento
		Document updateQuery = new Document("codigo", codigo);
		Document updateValues = 
				new Document("$push", 
						new Document("asientos_disponibles", asiento));
		
		vuelosColl.updateOne(updateQuery, updateValues);

	}
	 
	
	private Random random = new Random();
	private String generarCodigoVenta() {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	    StringBuilder sb = new StringBuilder(5);
	    
	    for (int i = 0; i < 5; i++) {
	           char c = chars[random.nextInt(chars.length)];
	           sb.append(c);
	    }
	    int num = (int) (1 + Math.random() * 1000);
	    return sb.toString().toUpperCase() + num;
	}
	
	private Vuelo documentToVuelo(Document doc) {
		return new Vuelo(doc.getString("codigo"), doc.getString("origen"), doc.getString("destino"),
					doc.getString("fecha"), doc.getString("hora"), doc.getInteger("plazas_totales"),
					doc.getInteger("plazas_disponibles"));
	}
	
	private Document vueloToDocument(Vuelo v) {
		return new Document("codigo", v.getCodigo())
					.append("origen", v.getSalida())
					.append("destino", v.getDestino())
					.append("fecha", v.getFecha())
					.append("hora", v.getHora())
					.append("plazas_totales", v.getPlazas())
					.append("plazas_disponibles", v.getPlazasLibres());
	}
	
	private Venta documentToVenta(Document doc) {
		return new Venta(doc.getInteger("asiento"), doc.getString("nombre"), doc.getString("apellido"),
					doc.getString("dni"), doc.getString("dniPagador"), doc.getString("tarjeta"),
					doc.getString("codigoVenta"));
	}
	
	private Document ventaToDocument(Venta v) {
		return new Document("asiento", v.getAsiento())
					.append("nombre", v.getNombre())
					.append("apellido", v.getApellido())
					.append("dni", v.getDni())
					.append("dniPagador", v.getDniPagador())
					.append("tarjeta", v.getTarjeta())
					.append("codigoVenta", v.getCodigoVenta());
	}
	
}
