//
//  DemoContentView.swift
//  DemoApp
//
//  Created by Meteor 丶Shower on 2022/5/18.
//

import SwiftUI
import Refresh

struct DemoContentView: View {
        
    @State private var selecteState: [String:String] = ["":""]
    @StateObject private var dataModel = DemoContentViewModel()
    @State var isShowing: Bool = false
    @State private var items: [ContentResult] = []
    @State private var headerRefreshing: Bool = false
    @State private var footerRefreshing: Bool = false
    @State private var noMore: Bool = false
    @State private var listState = ListState()
    
    var body: some View {
        NavigationView {
            VStack {
              ScrollView {
                  PullToRefreshView(footer: RefreshDefaultFooter()) {
                      LazyVGrid(columns: [GridItem(.flexible())], spacing: 15) {
                          ForEach(0 ..< 1) { index in
                              Section {
                                  ForEach(self.items) { item in
                                      RoundedRectangle(cornerRadius: 8)
                                          .foregroundColor(Color.white)
                                          .frame(height: 84)
                                          .overlay(DemoContentCell(model: item, selecteState: self.$selecteState).frame(height: 84))
                                  }
                              }
                          }
                      }
                      .padding()
                      .background(Color(UIColor.systemGroupedBackground))
                  }.environmentObject(listState)
              }
              .addPullToRefresh(isFooterRefreshing: $footerRefreshing, onFooterRefresh: loadMore)
              .overlay(Group {
                  if items.count == 0 {
                      ActivityIndicator(style: .medium)
                  } else {
                      EmptyView()
                  }
              })
              .onAppear { self.reload() }
                
            }
            .clipped()
            .navigationTitle("App")
            .navigationBarTitleDisplayMode(.automatic)
            .background(Color(UIColor.systemGroupedBackground))
        }
        .background(PullToRefreshNavigationView(action: {
                    DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
                        self.isShowing = false
                        self.items.removeAll()
                        self.items.append(contentsOf: self.dataModel.results)
                    }
                  }, isShowing: $isShowing))
    }
    
    func reload() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            self.items = self.dataModel.results
            self.headerRefreshing = false
            self.noMore = false
        }
    }
    
    func loadMore() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            self.items += self.dataModel.results
            self.noMore = self.items.count < 250
            if self.noMore {
                self.footerRefreshing = false
                listState.setNoMore(true)
            }
        }
    }
}

struct DemoContentView_Previews: PreviewProvider {
    static var previews: some View {
        DemoContentView()
    }
}
