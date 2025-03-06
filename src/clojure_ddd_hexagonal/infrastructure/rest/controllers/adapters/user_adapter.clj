(ns clojure-ddd-hexagonal.infrastructure.rest.controllers.adapters.user-adapter
  (:require [clojure-ddd-hexagonal.domain.user :as d-user]))

           (defn dto->domain [user-dto]
             (-> d-user/User
                 (assoc :id (:id user-dto))
                 (assoc :name (:name user-dto))
                 (assoc :email (:email user-dto))
                 (assoc :created-at (:created_at user-dto))))

           (defn domain->dto [domain-user]
             {:id (:id domain-user)
              :name (:name domain-user)
              :email (:email domain-user)
              :created_at (:created-at domain-user)})