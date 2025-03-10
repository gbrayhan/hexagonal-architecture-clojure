(ns infrastructure.rest.controllers.adapters.user-adapter
  (:require [domain.user :as d-user]
            [clojure.walk :refer [keywordize-keys]]))

(defn dto->domain [user-dto]
  (-> (keywordize-keys user-dto)
      (dissoc :created_at)
      (assoc :created-at nil)
      (d-user/map->User)))

(defn domain->dto [domain-user]
  {:id (:id domain-user)
   :name (:name domain-user)
   :email (:email domain-user)
   :created_at (:created-at domain-user)})

