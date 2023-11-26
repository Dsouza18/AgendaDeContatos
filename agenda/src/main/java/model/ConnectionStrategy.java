package Model;

import java.sql.Connection;

public interface ConnectionStrategy {
    Connection connect();
}
