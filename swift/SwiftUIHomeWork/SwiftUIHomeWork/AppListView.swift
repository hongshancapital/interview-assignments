//
//  AppListView.swift
//  SwiftUIHomeWork
//
//  Created by Yu jun on 2022/6/25.
//

import SwiftUI
import Combine
struct AppListView: View {
    @ObservedObject var publicData = PublicData()
    ///加载更多
    func loadingMessage(page: String, pageNumber: String) {
        APIClient.shareClient.getAppMessage(page: page, pageNumber: pageNumber) { modelList, error in
            if modelList != nil {
                self.publicData.showAppList += modelList!["showAppList"] as! [AppMessageModel]
            }
        }
    }
    
    var scrollView: some View {
        RefreshableScrollView(refreshing: self.$publicData.reflesh) {
            LazyVStack(alignment: .leading, spacing: 15) {
                ForEach(publicData.showAppList) { appModel in
                    AppListRowView(appmessageModel: appModel).environmentObject(self.publicData)
                        .frame(height: 80)
                        .background(Color.white)
                        .cornerRadius(10)
                }
                HStack {
                    Spacer()
                    if self.publicData.showAppList.count >= self.publicData.count {
                        Text("No more data.")
                            .foregroundColor(Color.gray)
                    } else {
                        HWIndicatorView(style: .medium, isShowing: true)
                        Text("Loading...")
                            .foregroundColor(.gray)
                            .onAppear {
                                self.publicData.page += 1
                                loadingMessage(page: "\(self.publicData.page)", pageNumber: "10")
                            }
                    }
                    Spacer()
                }
            }
            .padding()
        }
        .background(Color.gray.opacity(0.2))
    }
    var body: some View {
        NavigationView {
            Group {
                
                if self.publicData.showAppList.count > 0 {
                    scrollView
                } else {
                    HWIndicatorView(style: .large, isShowing: true)
                        .padding(.bottom, 200)
                }
            }
            .navigationTitle(Text("App"))
        }
        .navigationViewStyle(.stack)
    }
}

struct AppListView_Previews: PreviewProvider {
    static var previews: some View {
        AppListView()
    }
}
