package com.ensode.jakartaeebook.criteriaapi.namedbean;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.ensode.jakartaeebook.criteriaapi.entity.UsState;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import jakarta.persistence.metamodel.SingularAttribute;

@Named
@RequestScoped
public class CriteriaApiDemoBean {

  @PersistenceContext
  private EntityManager entityManager;

  private Stream<UsState> matchingStatesStream;
  private List<UsState> matchingStatesList;

  public String findStates() {
    String retVal = "confirmation";
    try {
      CriteriaBuilder criteriaBuilder = entityManager.
              getCriteriaBuilder();
      CriteriaQuery<UsState> criteriaQuery = criteriaBuilder.
              createQuery(UsState.class);
      Root<UsState> root = criteriaQuery.from(UsState.class);

      Metamodel metamodel = entityManager.getMetamodel();
      EntityType<UsState> usStateEntityType = metamodel.entity(
              UsState.class);
      SingularAttribute<UsState, String> usStateAttribute
              = usStateEntityType.getDeclaredSingularAttribute("usStateNm",
                      String.class);
      Path<String> path = root.get(usStateAttribute);
      Predicate predicate = criteriaBuilder.like(
              path, "New%");
      criteriaQuery = criteriaQuery.where(predicate);

      TypedQuery typedQuery = entityManager.createQuery(
              criteriaQuery);

      matchingStatesStream = typedQuery.getResultStream();

      if (matchingStatesStream != null) {
        matchingStatesList = matchingStatesStream.collect(Collectors.toList());
      }

    } catch (Exception e) {
      retVal = "error";
      e.printStackTrace();
    }

    return retVal;
  }

  public List<UsState> getMatchingStatesList() {
    return matchingStatesList;
  }

  public void setMatchingStatesList(List<UsState> matchingStatesList) {
    this.matchingStatesList = matchingStatesList;
  }

}
