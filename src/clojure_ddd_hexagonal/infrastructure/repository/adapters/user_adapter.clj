(ns clojure-ddd-hexagonal.infrastructure.repository.adapters.user-adapter
    (:require [clojure-ddd-hexagonal.domain.user :as d-user]))

(defn db->domain [db-record]
  (-> d-user/User
      (assoc :id (:id db-record))
      (assoc :name (:name db-record))
      (assoc :email (:email db-record))
      (assoc :created-at (:created_at db-record))))

(defn domain->db [domain-user]
  {:name (:name domain-user)
   :email (:email domain-user)})


