//
//  AppListRootView.swift
//  scdt_interview (iOS)
//
//  Created by Ray Tao on 2022/7/14.
//

import SwiftUI

struct AppListRootView: View {
    @EnvironmentObject var store: Store

    var body: some View {
        NavigationView {
            ZStack {
                Color.gray.opacity(0.1).edgesIgnoringSafeArea(.all)
                if store.appState.appList.list == nil {
                    ScrollView {
                        VStack {

                            if store.appState.appList.appsLoadingError != nil {
                                RetryButton {
                                    self.store.dispatch(.loadAppList)
                                }.offset(y: -40)
                            } else {
                                ProgressView()
                                    .offset(y: -40)
                                    .frame(width: 50, height: 50, alignment: .center)
                                    .onAppear {
                                        self.store.dispatch(.loadAppList)
                                    }
                            }
                        }
                        
                    }.navigationBarTitle("App")
                } else {
                    AppListView()
                }

            }
            
        }
    }

    struct RetryButton: View {
        let block: () -> Void

        var body: some View {
            Button(action: {
                self.block()
            }) {
                HStack {
                    Text("Retry")
                }
                .font(.system(size: 16, weight: .regular))
                .foregroundColor(.gray)
                .padding(6)
                .background(
                    RoundedRectangle(cornerRadius: 8)
                        .stroke(Color.gray)
                )
            }
        }
    }
}

struct AppListRootView_Previews: PreviewProvider {
    static var previews: some View {
        AppListRootView()
    }
}
