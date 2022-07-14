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
                    VStack {
                        Spacer()
                        ProgressView()
                            .onAppear {
                                self.store.dispatch(.loadAppList)
                            }
                        Spacer()
                    }
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
                    .navigationBarTitle("App")
                } else {
                    AppListView()
                }
            }
        }
        //fix LayoutConstraints issue https://stackoverflow.com/questions/65316497/swiftui-navigationview-navigationbartitle-layoutconstraints-issue
        .navigationViewStyle(.stack)
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
