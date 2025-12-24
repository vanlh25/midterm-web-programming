package vn.iotstar.config;

import jakarta.persistence.*;
import vn.iotstar.util.Constant;
/**
 * Class cấu hình JPA
 * Dùng để tạo EntityManager và quản lý kết nối database
 */

@PersistenceContext
public class JPAConfig {

    private static EntityManagerFactory factory;

    public static EntityManager getEntityManager() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(Constant.PERSISTENCE_UNIT_NAME);
        }
        return factory.createEntityManager();
    }
}