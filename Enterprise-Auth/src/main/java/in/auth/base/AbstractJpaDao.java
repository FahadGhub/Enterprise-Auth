package in.auth.base;

import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public abstract class AbstractJpaDao<T, I> {

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	public T findEntityById(Class<T> clazz, I id) {
		try {
			return entityManager.find(clazz, id);
		} catch (NoResultException e) {
			return null;
		}

	}

	public T findEntityByMultipleIds(Class<T> clazz, Map<String, Object> columnValueMap) {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
			Root<T> root = criteriaQuery.from(clazz);

			Predicate[] predicates = new Predicate[columnValueMap.size()];
			int index = 0;
			for (Map.Entry<String, Object> entry : columnValueMap.entrySet()) {
				predicates[index] = criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue());
				index++;
			}

			criteriaQuery.where(predicates);

			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<T> findEntityListByMultipleIds(Class<T> clazz, Map<String, Object> columnValueMap) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
		Root<T> root = criteriaQuery.from(clazz);

		Predicate[] predicates = new Predicate[columnValueMap.size()];
		int index = 0;
		for (Map.Entry<String, Object> entry : columnValueMap.entrySet()) {
			predicates[index] = criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue());
			index++;
		}

		criteriaQuery.where(predicates);

		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll(Class<T> clazz) {
		try {
			return entityManager.createQuery("from " + clazz.getName()).getResultList();
		} catch (NoResultException e) {
			return null;
		}

	}

	public T save(final T entity) {
		entityManager.persist(entity);
		return entity;
	}

	public T update(final T entity) {
		return entityManager.merge(entity);
	}

	public void delete(final T entity) {
		entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
	}

	public I deleteById(Class<T> clazz, I entityId) {
		try {
			final T entity = findEntityById(clazz, entityId);
			this.delete(entity);
			return entityId;
		} catch (NoResultException e) {
			return null;
		}

	}
}
