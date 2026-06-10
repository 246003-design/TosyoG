package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Book;

public class BookDAO extends BaseDAO{
//DBManagerを継承
	public BookDAO(Connection conn) {
		super(conn);
	}
	public List<Book>findAll(){
		List<Book> list = new ArrayList<>();
		String sql ="SELECT id, bookInfoId, bookNumber, layoutId, createdAt, updatedAt, deletedAt"
				+ "FROM book WEHER deletedAt IS NULL";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()){
			
			while(rs.next()) {
				Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setBookInfoId(rs.getInt("bookInfoId"));
                book.setBookNumber(rs.getInt("bookNumber"));
                book.setLayoutId(rs.getInt("layoutId"));
                book.setCreatedAt(rs.getTimestamp("createdAt"));
                book.setUpdatedAt(rs.getTimestamp("updatedAt"));
                book.setDeletedAt(rs.getTimestamp("deletedAt"));
                
                list.add(book);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
