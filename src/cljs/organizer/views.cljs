(ns organizer.views
  (:require [re-frame.core :as re-frame]))


(defn add-event [event]
  (re-frame/dispatch [:validation-event event])
  (re-frame/dispatch [:add-event event]))

(defn delete-event [id]
  (re-frame/dispatch [:delete-event id]))

(defn render-list [{:keys [id] :as data}]
  [:li {:key id} data [:input {:type "submit" :value "Delete" :on-click #(delete-event id)}]])


(defn increment-id! [event]
  (let [{:keys [id]} @event]
    (swap! event assoc :id (inc id))))




(defn submit-button [input]
  [:input {:type "submit" :on-click #(do (increment-id! input)
                                         (add-event @input)) }])

(defn validate []
  (let [validation (re-frame/subscribe [:validation])]
   (if @validation
    [:div]
    [:div "please enter valid input"]
    )))


(defn create-form [validation]
  (let [input (atom {:event "" :start-time "" :end-time "" :id 0})
        ]
   [:div {} 
    "Event:" 
    [:br]
    [:input {:type "text" :name "event" 
             :on-change #(swap! input assoc :event (-> % .-target .-value))}]    
    [:br]
    "Start Time: "
    [:input {:type "datetime-local" :name "start-time"
             :on-change #(swap! input assoc :start-time (-> % .-target .-value))}]
    [:br]
    "End Time: "
    [:input {:type "datetime-local" :name "end-time"
             :on-change #(swap! input assoc :end-time (-> % .-target .-value))}]

    [:br]
    [validate]
    [submit-button input]]))


(defn main-panel []
  (let [todo (re-frame/subscribe [:todo])]
    (fn []
      [:div [:ul (for [event @todo] (render-list event))] 
       [create-form]])))
