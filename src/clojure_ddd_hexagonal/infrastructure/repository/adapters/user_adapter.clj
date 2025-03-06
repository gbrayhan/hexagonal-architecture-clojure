(ns clojure-ddd-hexagonal.infrastructure.repository.adapters.user-adapter
  (:require [clojure-ddd-hexagonal.domain.user :as d-user]))

(defn db->domain [db-record]
  (-> db-record
      (assoc :created-at (:created_at db-record))
      (dissoc :created_at)
      (d-user/map->User)))

(defn domain->db [domain-user]
  {:name  (:name domain-user)
   :email (:email domain-user)})
