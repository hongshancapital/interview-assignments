//
//  AppList.swift
//   interviewiOS
//
//  Created by aa on 2022/5/4
//

import SwiftUI

struct AppList: View {
    @ObservedObject var viewModel: ViewModel = .init()
    var body: some View {
        NavigationView {
            List {
                ForEach.init(self.viewModel.items, id: \.self) { value in
                    AppRow(appInfo: value)
                }
                HStack {
                    if self.viewModel.canLoadMore {
                        if self.viewModel.items.count != 0 {
                            Spacer()
                            Text("Loading ...")
                                .background(Color.clear)
                                .onAppear {
                                    debugPrint("onAppear")
                                    self.loadMore()
                                }
                            Spacer()
                        }
                    }
                    else {
                        Spacer()
                        Text("No more data.")
                            .background(Color.clear)
                        Spacer()
                    }
                }
                .listRowBackground(Color.clear)
                .listRowSeparator(.hidden)
            }
            .task {
                await refresh()
            }
            .refreshable {
                await refresh()
            }
            .navigationTitle("App")
        }
    }
    
    func loadMore() {
        self.viewModel.loadMore()
    }
    
    func refresh() async -> Void {
        self.viewModel.items.removeAll()
        if self.viewModel.canLoadMore {
            self.loadMore()
        }
    }
}


struct AppInfosList_Previews: PreviewProvider {
    static var previews: some View {
        ForEach(["iPhone SE", "iPhone XS Max"], id: \.self) { deviceName in
            AppList()
                .previewDevice(PreviewDevice(rawValue: deviceName))
                .previewDisplayName(deviceName)
        }
        .environmentObject(UserData())
    }
}
