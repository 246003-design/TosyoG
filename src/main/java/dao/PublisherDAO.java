package dao;

import java.sql.Connection;

public class PublisherDAO extends BaseDAO{

	public PublisherDAO(Connection conn) {
		super(conn);
	}

	public Optional<publisher>findById(int id){
}
