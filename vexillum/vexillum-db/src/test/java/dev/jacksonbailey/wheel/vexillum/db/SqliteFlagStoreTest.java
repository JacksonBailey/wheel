package dev.jacksonbailey.wheel.vexillum.db;

import static org.junit.jupiter.api.Assertions.*;

import dev.jacksonbailey.vexillum.db.jooq.Tables;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FlywayMigrator.class)
class SqliteFlagStoreTest {

  private Connection connection;
  private DSLContext create;

  @BeforeEach
  void beforeEach() {
    try {
      connection = DriverManager.getConnection(DbConfig.jdbcUrl);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    create = DSL.using(connection, SQLDialect.SQLITE);
  }

  @AfterEach
  void afterEach() {
    create = null;
    try {
      connection.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void test() {
    assertEquals(0, create.fetchCount(Tables.FLAG));
    var newFlag = create.newRecord(Tables.FLAG);
    newFlag.setName("WOW");
    newFlag.setState(1);
    newFlag.store();
    assertEquals(1, create.fetchCount(Tables.FLAG));
    var result = create.fetchOne(Tables.FLAG, Tables.FLAG.NAME.eq("WOW"));
    assertEquals(1, result.getState());
  }

}
