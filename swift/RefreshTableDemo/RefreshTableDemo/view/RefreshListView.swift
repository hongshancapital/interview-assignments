//
//  RefreshListView.swift
//  RefreshTableDemo
//
//  Created by yaojinhai on 2022/12/15.
//

import Foundation
import SwiftUI

struct RefreshListView: View {
    
    
    @State private var data = [ItemCellDataJson]()
    @State private var headerRefreshing: Bool = false
    @State private var footerRefreshing: Bool = false
    @State private var noMore: Bool = false
    @State private var error: NSError? = nil
    
    @State private var pageIndex = 1
    private let jsonModel = LoadDataModel()
    
    
    var body: some View {
        ScrollView {
            if data.count > 0 {
                RefreshHeader(refreshing: $headerRefreshing, action: {
                    
                    pageIndex = 1
                    loadData()
                    
                }) { progress in
                    if self.headerRefreshing {
                        ActivityIndicator(isLoading: .constant(true), style: .medium)
                    } else {
                        SimplePullToRefreshView()
                    }
                }
            }
            
            ForEach($data) { item in
                TableItemCellView(itemJson: item)
                    .background(Color(uiColor: .systemBackground))
                .id(item.id)
            }
            .padding([.trailing,.leading], 16)
            .background(Color(uiColor: .systemBackground))
            
            if data.count > 0 {
                RefreshFooter(refreshing: $footerRefreshing, action: {
                    pageIndex += 1
                    loadData()
                }) {
                    if self.noMore {
                        Text("No more data !")
                            .foregroundColor(.secondary)
                            .padding()
                    } else {
                        SimplePullToRefreshView()
                        
                    }
                }
                .noMore(noMore)
                .preload(offset: 50)
            }
        }
        .enableRefresh()
        .overlay(Group {
            if error != nil {
                AlertMessageView(error: $error, time: 2)
            }else if data.count == 0 {
                ActivityIndicator(isLoading: .constant(true),style: .medium)
            } else {
                EmptyView()
            }
        }).task { 
            loadData()
        }
        
    }
    
    private func loadData()  {
        
        jsonModel.loadDataAction(pageIndex: pageIndex) { result, error in
            headerRefreshing = false
            footerRefreshing = false
            self.error = error
            if let list = result{
                if pageIndex == 1 {
                    data = list 
                }else {
                    data.append(contentsOf: list)
                }
                
                self.noMore = list.count == 0
            }
        }
    }
    
    
}


struct SimplePullToRefreshView: View {
    
    var body: some View {
        Text("      ")
    }
}
