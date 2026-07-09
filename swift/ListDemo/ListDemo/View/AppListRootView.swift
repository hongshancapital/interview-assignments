//
//  AppListRootView.swift
//  ListDemo
//
//  Created by 王明友 on 2023/6/10.
//

import SwiftUI
import Combine

struct AppListRootView: View {

    @EnvironmentObject var store: AppStore

    var body: some View {
        NavigationView {
            if store.appState.appList.apps == nil {
                if store.appState.appList.appsLoadingError != nil {
                    Retry {
                        self.store.dispatch(.refresh)
                    }.offset(y: -40)
                } else {
                    ProgressView()
                        .offset(y: -40)
                        .onAppear {
                            self.store.dispatch(.refresh)
                        }
                }
            } else {
                AppListView()
                    .navigationBarTitle("APP")
                    .listStyle(.plain)
                    .background(Color(uiColor: .systemGroupedBackground))
            }
        }
    }

    struct Retry: View {
        let block: () -> Void
        var body: some View {
            Button(action: {
                self.block()
            }) {
                HStack {
                    Image(systemName: "info.circle")
                    Text("Retry")
                }
                .font(.system(size: 16, weight: .regular))
                .foregroundColor(.gray)
                .padding(6)
            }
        }
    }

}
