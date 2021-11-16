package mate.academy.spring.dao.impl;

import java.util.Optional;
import mate.academy.spring.dao.AbstractDao;
import mate.academy.spring.dao.RoleDao;
import mate.academy.spring.exception.DataProcessingException;
import mate.academy.spring.model.Role;
import mate.academy.spring.model.RoleType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends AbstractDao<Role> implements RoleDao {
    public RoleDaoImpl(SessionFactory factory) {
        super(factory, Role.class);
    }

    @Override
    public Optional<Role> getRoleByName(String name) {
        try (Session session = factory.openSession()) {
            Query<Role> getRoleByName = session.createQuery(
                    "FROM Role WHERE name = :name", Role.class);
            getRoleByName.setParameter("name", RoleType.getEnumValue(name));
            return getRoleByName.uniqueResultOptional();
        } catch (Exception e) {
            throw new DataProcessingException("Role with name " + name + " not found", e);
        }
    }
}