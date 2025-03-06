(ns clojure-ddd-hexagonal.application.usecase.adapters.user-adapter
    (:require [clojure-ddd-hexagonal.infrastructure.rest.controllers.adapters.user-adapter :as controllers-adapter]))

(defn convert-input [user-dto]
  (controllers-adapter/dto->domain user-dto))

(defn convert-output [domain-user]
  (controllers-adapter/domain->dto domain-user))
