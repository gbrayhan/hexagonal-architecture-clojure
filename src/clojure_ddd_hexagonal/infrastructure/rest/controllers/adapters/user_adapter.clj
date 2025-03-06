(ns clojure-ddd-hexagonal.infrastructure.rest.controllers.adapters.user-adapter
  (:require [clojure-ddd-hexagonal.domain.user :as d-user]
            [clojure.walk :refer [keywordize-keys]]))

(defn dto->domain [user-dto]
  (-> (keywordize-keys user-dto)
      ;; Eliminamos la llave :created_at del DTO ya que la entidad de dominio usa :created-at
      (dissoc :created_at)
      ;; Si se requiere, se puede dejar en nil o conservar otro valor
      (assoc :created-at nil)
      (d-user/map->User)))

(defn domain->dto [domain-user]
  {:id (:id domain-user)
   :name (:name domain-user)
   :email (:email domain-user)
   :created_at (:created-at domain-user)})

