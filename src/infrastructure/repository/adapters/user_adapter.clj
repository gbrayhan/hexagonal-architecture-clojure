(ns infrastructure.repository.adapters.user-adapter
  (:require [domain.user :as d-user]
            [clojure.walk :refer [keywordize-keys]]))

(defn lower-case-keys [m]
  (into {} (for [[k v] m]
             [(-> k name .toLowerCase keyword) v])))

(defn db->domain [db-record]
  (let [record (if (map? db-record)
                 (-> db-record
                     keywordize-keys
                     lower-case-keys)
                 db-record)]
    (-> record
        (assoc :created-at (:created_at record))
        (dissoc :created_at)
        (d-user/map->User))))

(defn domain->db [domain-user]
  {:name  (:name domain-user)
   :email (:email domain-user)})
