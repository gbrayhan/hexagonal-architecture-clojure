(ns clojure-ddd-hexagonal.infrastructure.repository.adapters.user-adapter
  (:require [clojure-ddd-hexagonal.domain.user :as d-user]
            [clojure.walk :refer [keywordize-keys]]))

(defn db->domain [db-record]
  (let [record (if (map? db-record)
                 (keywordize-keys db-record)
                 db-record)]
    (-> record
        (assoc :created-at (:created_at record))
        (dissoc :created_at)
        (d-user/map->User))))

(defn domain->db [domain-user]
  {:name  (:name domain-user)
   :email (:email domain-user)})
