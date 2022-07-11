//
//  ContentView.swift
//  AppList
//
//  Created by haojiajia on 2022/7/7.
//

import SwiftUI

struct ContentView: View {
    
    @EnvironmentObject var store: AppStore
    
    var body: some View {
        NavigationView {
            if store.appState.apps.count == 0 {
                if store.appState.appsLoadingError != nil {
                    RetryButton {
                        self.store.dispatch(.loadApps)
                    }.offset(y: -40)
                } else {
                    ProgressView()
                        .onAppear {
                            self.store.dispatch(.loadApps)
                        }
                }
            } else {
                AppListView()
                    .refreshable {
                        self.store.dispatch(.loadApps)
                    }
                    .navigationBarTitle("App")
            }
        }
        .navigationViewStyle(StackNavigationViewStyle())
        .background(Color(UIColor.systemGroupedBackground))
    }
    
}

#if DEBUG
struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView().environmentObject(AppStore.sample)
    }
}
#endif

struct RetryButton: View {
    
    let block: () -> Void
    
    var body: some View {
        Button(action: {
            self.block()
        }) {
            HStack {
                Image(systemName: "arrow.clockwise")
                Text("loading failed, click Retry")
            }
            .font(.system(size: 16, weight: .regular))
            .foregroundColor(.gray)
            .padding(6)
        }
    }
}
