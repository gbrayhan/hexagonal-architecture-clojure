(ns clojure-ddd-hexagonal.application.usecase.user
  (:require [clojure-ddd-hexagonal.domain.user :as d-user]
            [clojure-ddd-hexagonal.application.usecase.adapters.user-adapter :as usecase-adapter]))

(defn create-user [repository user-dto]
  (let [user (usecase-adapter/convert-input user-dto)]
    (d-user/create-user repository user)))

(defn get-all-users [repository]
  (d-user/get-all repository))

(defn get-by-id [repository id]
  (d-user/get-by-id repository id))

(defn update-user [repository id user-dto]
  (let [user (usecase-adapter/convert-input user-dto)]
    (d-user/update-user repository id user)))

(defn delete-user [repository id]
  (d-user/delete-user repository id))
