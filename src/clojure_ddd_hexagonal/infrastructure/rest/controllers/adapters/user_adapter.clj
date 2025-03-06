(ns clojure-ddd-hexagonal.infrastructure.rest.controllers.adapters.user-adapter
  (:require [clojure-ddd-hexagonal.domain.user :as d-user]))

(defn dto->domain [user-dto]
  (-> user-dto
      (assoc :created-at (:created_at user-dto))
      (dissoc :created_at)
      (d-user/map->User)))

(defn domain->dto [domain-user]
  {:id (:id domain-user)
   :name (:name domain-user)
   :email (:email domain-user)
   :created_at (:created-at domain-user)})
