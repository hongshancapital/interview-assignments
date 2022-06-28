//
//  ContentView.swift
//  Demo
//
//  Created by milomiao on 2022/6/20.
//

import SwiftUI

struct ContentView: View {
    @EnvironmentObject var provider: AppDataProvider

    @State var isLoading = true
    @State var refresh = false

    @Binding var feed: FeedProvider
    
    var body: some View {
        if (isLoading) {
            ProgressView().onAppear() {
                Task(priority: .background) {
                    await refreshAction()
                    isLoading = false
                }
            }
        } else {
            NavigationView {
                List {
                    ForEach(feed.infos ?? []) { info in
                        InfoCell(info: info).listRowInsets(EdgeInsets())
                    }
                    HStack {
                        Spacer()
                        if feed.hasMore {
                            ProgressView()
                            .padding(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 5))
                        }

                        Text(feed.moreDescription).onAppear {
                            if (feed.hasMore) {
                                Task(priority: .background) {
                                    refresh = true
                                    await loadMoreAction()
                                    refresh = false
                                }
                            }
                        }
                        Spacer()

                    }
                }
                .background(.green)
                .navigationTitle("App")
                .refreshable {
                    refresh = true
                    await refreshAction()
                    refresh = false
                }
            }.navigationViewStyle(StackNavigationViewStyle())
        }
    }
    
    func refreshAction() async {
        feed.resetPosition()
        let infos = await FeedRequest.loadData(from: feed.from, length: feed.step)
        updateLikeState(infos: infos)
        feed.infos = infos
        feed.updatePostion()
    }
    
    func loadMoreAction() async {
        if let infos = await FeedRequest.loadData(from: feed.from, length: feed.step) {
            updateLikeState(infos: infos)
            feed.infos?.append(contentsOf: infos)
            feed.updatePostion()
        }
    }
    
    func updateLikeState(infos: [AppItemInfo]?) {
        infos?.forEach({ item in
            if (provider.store.likes.contains(item.id!)) {
                item.isLike = true
            }
        })
    }
    
    static func createAppInfo() -> AppItemInfo {
        let appInfo = AppItemInfo()
        appInfo.title = "hello world"
        appInfo.subtitle = "hello world hello world hello world hello world !"
        appInfo.iconURL = "https://www.hko.gov.hk/images/HKOWxIconOutline/pic53.png"
        appInfo.isLike = false
        return appInfo
    }
}

#if DEBUG
struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView(feed: .constant(FeedProvider())).environmentObject(AppDataProvider.shared)
    }
}
#endif

