(ns organizer.events
  (:require [re-frame.core :as re-frame]
            [organizer.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
  :add-event
  (fn [{:keys [todo] :as db} [_ event]] 
    (let [m-todo (conj todo event)]
     (assoc db :todo m-todo))))
