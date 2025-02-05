(ns clojure-ddd-hexagonal.application.usecase.user
  (:require [clojure-ddd-hexagonal.domain.user :as d-user]))

(defn create-user [repository user]
  (d-user/create-user repository user))

(defn get-all-users [repository]
  (d-user/get-all repository))

(defn get-by-id [repository id]
  (d-user/get-by-id repository id))

(defn update-user [repository id usuario]
  (d-user/update-user repository id usuario))

(defn delete-user [repository id]
  (d-user/delete-user repository id))
