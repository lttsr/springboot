package sample.context.orm;

import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import sample.context.DomainEntity;
import sample.context.Repository;

@RequiredArgsConstructor
public abstract class OrmRepository implements Repository {

    /**
     * Returns the EntityManager to be managed.
     * <p>
     * Return the EntityManager of the data source you wish to manage by
     * inheritance.
     */
    public abstract EntityManager em();

    /**
     * Set the EntityManager to be managed.
     */
    public abstract void em(EntityManager em);

    /**
     * Generates simple accessors for ORM operations.
     * <p>
     * OrmTemplate is generated for each call.
     */
    // public OrmTemplate tmpl() {
    // return OrmTemplate.of(em());
    // }

    /** {@inheritDoc} */
    @Override
    public <T extends DomainEntity> Optional<T> get(Class<T> clazz, Object id) {
        T m = em().find(clazz, id);
        if (m != null)
            m.hashCode(); // force loading
        return Optional.ofNullable(m);
    }

    /** {@inheritDoc} */
    @Override
    public <T extends DomainEntity> T load(Class<T> clazz, Object id) {
        try {
            T m = em().getReference(clazz, id);
            m.hashCode(); // force loading
            return m;
        } catch (EntityNotFoundException e) {
            return null;
        }

    }

    /** {@inheritDoc} */
    @Override
    public <T extends DomainEntity> T loadForUpdate(Class<T> clazz, Object id) {
        T m = em().find(clazz, id, LockModeType.PESSIMISTIC_WRITE);
        if (m == null) {
            return null;
        }
        m.hashCode(); // force loading
        return m;
    }

    /** {@inheritDoc} */
    @Override
    public <T extends DomainEntity> boolean exists(Class<T> clazz, Object id) {
        return get(clazz, id).isPresent();
    }

    /** {@inheritDoc} */
    @Override
    public <T extends DomainEntity> T save(T entity) {
        em().persist(entity);
        return entity;
    }

    /** {@inheritDoc} */
    @Override
    public <T extends DomainEntity> T saveOrUpdate(T entity) {
        return em().merge(entity);
    }

    /** {@inheritDoc} */
    @Override
    public <T extends DomainEntity> T update(T entity) {
        return em().merge(entity);
    }

    /** {@inheritDoc} */
    @Override
    public <T extends DomainEntity> T delete(T entity) {
        em().remove(entity);
        return entity;
    }

    /**
     * Synchronize all non-persistent entities in the session cache with the DB (SQL
     * execution).
     * <p>
     * Call it at the point where you want to clarify the timing of SQL issuance. In
     * cases where the session cache is strained by batch processing, etc. In cases
     * where the session cache is under memory constraints, such as batch
     * processing, #flushAndClear should be called periodically to prevent the
     * session cache from becoming bloated.
     */
    public OrmRepository flush() {
        em().flush();
        return this;
    }

    /**
     * Initialize session cache after synchronizing non-persistent entities in
     * JPA session cache with DB.
     * <p>
     * In many cases, such as batch processing that generates a large number of
     * updates, the session cache that is implicitly maintained can be a major
     * problem due to memory constraints. Periodically invoke this process to
     * maintain the session cache at a fixed size.
     */
    public OrmRepository flushAndClear() {
        em().flush();
        em().clear();
        return this;
    }

}