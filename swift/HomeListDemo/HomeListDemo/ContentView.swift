//
//  ContentView.swift
//  HomeListDemo
//
//  Created by yaojinhai on 2022/9/22.
//

import SwiftUI
import Combine

struct ContentView: View {
    

    @State private var pageIndex = 1
    @State private var headerRefreshing: Bool = false
    @State private var footerRefreshing: Bool = false
    
    //  第一次刷新
    @State private var isRefreshing = true

    
    // TODO: - 这里做一个临时保存是否点击了喜欢 
    @State private var selectedItemDict = [Int: Bool]()

    
    @EnvironmentObject var itemModel: LoadDataModel

    
    var body: some View {
        
        NavigationView { 
            scrollListView.onAppear { 
                loadDataAction()
            }
            
        }
    }
    
    private func loadDataAction() {
        
    
        itemModel.loadDataAction(pageIndex: pageIndex) {
            self.isRefreshing = false
            self.headerRefreshing = false
            self.footerRefreshing = false

        }
    }
    
  
    private var scrollListView: some View {
        
        VStack { 
            ScrollView {
                
                if itemModel.dataSourceList.count > 0 || self.isRefreshing == false {
                    RefreshHeader(refreshing: $headerRefreshing, action: {
                        self.pageIndex = 1
                        loadDataAction()
                    }) { progress in
                        if self.headerRefreshing {
                            ActivityIndicator(style: .medium)
                        } else {
                            SimplePullToRefreshView(progress: progress)
                        }
                    }
                }
                
                VStack { 
                    ForEach(itemModel.dataSourceList) { item in
                        ListItemCellView(itemJson: item, isSelected:.init(get: { 
                            self.selectedItemDict[item.id] == true
                        }, set: { newValue in
                            self.selectedItemDict[item.id] = newValue
                        })).background(Color(uiColor: .secondarySystemBackground))
                    }.padding([.trailing,.leading], 16).background(Color(uiColor: .secondarySystemBackground))
                    
                }
                
                if itemModel.dataSourceList.count > 0 {
                    RefreshFooter(refreshing: $footerRefreshing, action: {
                        self.pageIndex += 1
                        self.loadDataAction()
                    }) {
                        if self.itemModel.isHaveMore == false {
                            Text("No more data !")
                                .foregroundColor(.secondary)
                                .padding()
                        } else {
                            ActivityIndicator(style: .medium)
                                .padding()
                        }
                    }
                    .noMore(self.itemModel.isHaveMore == false)
                    .preload(offset: 50)
                }
                
                
            }.enableRefresh()
                .overlay(Group {
                    
                    if isRefreshing {
                        ActivityIndicator(style: .medium)
                    } else {
                        EmptyView()
                    } 
                    
                    
                }).background(Color(uiColor: .secondarySystemBackground))
            
        }.navigationBarTitle("App List").overlay(ToastErrorView(error: .init(get: { 
            itemModel.error
        }, set: { newValue in
            itemModel.error = newValue
        }), time: 2).background(Color.clear))
    }
    

    
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
//        ContentView(listDataSource: [.init(artworkUrl60: "", trackCensoredName: "", description: "", trackId: 0)])
        ContentView()

    }
}

struct SimplePullToRefreshView: View {
    let progress: CGFloat
    
    var body: some View {
        Text("      ")
    }
}
