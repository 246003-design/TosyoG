//継承してdaoを作ってください。
package dao;

import java.sql.Connection;

public class CategoryDAO extends BaseDAO{

	public CategoryDAO(Connection conn) {
		super(conn);
	}

}
