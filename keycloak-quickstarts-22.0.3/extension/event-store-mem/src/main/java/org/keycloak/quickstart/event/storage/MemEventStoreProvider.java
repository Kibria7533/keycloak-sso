///*
// * Copyright 2016 Red Hat, Inc. and/or its affiliates
// * and other contributors as indicated by the @author tags.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package org.keycloak.quickstart.event.storage;
//
//import org.keycloak.events.Event;
//import org.keycloak.events.EventQuery;
//import org.keycloak.events.EventStoreProvider;
//import org.keycloak.events.EventType;
//import org.keycloak.events.admin.AdminEvent;
//import org.keycloak.events.admin.AdminEventQuery;
//import org.keycloak.events.admin.OperationType;
//import org.keycloak.models.KeycloakSession;
//import org.keycloak.models.RealmModel;
//
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Set;
//
///**
// * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
// */
//public class MemEventStoreProvider implements EventStoreProvider {
//    private final List<Event> events;
//    private final Set<EventType> excludedEvents;
//    private final List<AdminEvent> adminEvents;
//    private final Set<OperationType> excludedOperations;
//    private final KeycloakSession session;
//
//    public MemEventStoreProvider(List<Event> events, Set<EventType> excludedEvents,
//            List<AdminEvent> adminEvents, Set<OperationType> excludedOperations, KeycloakSession session) {
//        this.events = events;
//        this.excludedEvents = excludedEvents;
//
//        this.adminEvents = adminEvents;
//        this.excludedOperations = excludedOperations;
//
//        this.session = session;
//    }
//
//    @Override
//    public EventQuery createQuery() {
//        return new MemEventQuery(new LinkedList<>(events));
//    }
//
//    @Override
//    public void clear() {
//
//    }
//
//    @Override
//    public void clear(RealmModel realm) {
//        synchronized(events) {
//            Iterator<Event> itr = events.iterator();
//            while (itr.hasNext()) {
//                if (itr.next().getRealmId().equals(realm.getId())) {
//                    itr.remove();
//                }
//            }
//        }
//    }
//
//    @Override
//    public void clear(RealmModel realm, long olderThan) {
//        synchronized(events) {
//            Iterator<Event> itr = events.iterator();
//            while (itr.hasNext()) {
//                Event e = itr.next();
//                if (e.getRealmId().equals(realm.getId()) && e.getTime() < olderThan) {
//                    itr.remove();
//                }
//            }
//        }
//    }
//
//    @Override
//    public void onEvent(Event event) {
//        if (excludedEvents == null || !excludedEvents.contains(event.getType())) {
//            events.add(0, event);
//        }
//    }
//
//    @Override
//    public AdminEventQuery createAdminQuery() {
//        return new MemAdminEventQuery(new LinkedList<>(adminEvents));
//    }
//
//    @Override
//    public void clearAdmin() {
//
//    }
//
//    @Override
//    public void clearAdmin(RealmModel realm) {
//        synchronized(adminEvents) {
//            Iterator<AdminEvent> itr = adminEvents.iterator();
//            while (itr.hasNext()) {
//                if (itr.next().getRealmId().equals(realm.getId())) {
//                    itr.remove();
//                }
//            }
//        }
//    }
//
//    @Override
//    public void clearAdmin(RealmModel realm, long olderThan) {
//        synchronized(adminEvents) {
//            Iterator<AdminEvent> itr = adminEvents.iterator();
//            while (itr.hasNext()) {
//                AdminEvent e = itr.next();
//                if (e.getRealmId().equals(realm.getId()) && e.getTime() < olderThan) {
//                    itr.remove();
//                }
//            }
//        }
//    }
//
//    @Override
//    public void clearExpiredEvents() {
//        session.realms().getRealmsStream().forEach(r -> {
//            if (r.getEventsExpiration() > 0) {
//                long olderThan = System.currentTimeMillis() - r.getEventsExpiration() * 1000;
//                clear(r, olderThan);
//            }
//        });
//    }
//
//    @Override
//    public void onEvent(AdminEvent adminEvent, boolean includeRepresentation) {
//        if (excludedOperations == null || !excludedOperations.contains(adminEvent.getOperationType())) {
//            adminEvents.add(0, adminEvent);
//        }
//    }
//
//    @Override
//    public void close() {
//    }
//
//}
