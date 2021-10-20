//
//  SearchListView.swift
//  SwiftUIDemo
//
//  Created by weizhao on 2021/10/17.
//  Copyright © 2021 weizhao. All rights reserved.
//

import SwiftUI

struct SearchListView: View {
    
    @ObservedObject var viewModel: RepositoryListViewModel
    
    @State private var pageIndex: Int = 0
    private let pageSize: Int = 10
    
    var body: some View {
        NavigationView{
            if #available(iOS 15.0, *) {
                List {
                    if viewModel.state == .isLoading {
                        Text("Loading...").padding(.vertical).foregroundColor(.gray).background(.clear).multilineTextAlignment(.center)
                    } else if viewModel.state == .noData {
                        Text("no result").padding(.vertical).foregroundColor(.gray).background(.clear).multilineTextAlignment(.center)
                    } else {
                        
                        viewModel.errorMessage.map(Text.init)?
                            .lineLimit(nil)
                            .multilineTextAlignment(.center)
                        // 数据实体View模块
                        ForEach(viewModel.repositorySection) { section in
                            Section(header: Text(section.category)) {
                                ForEach(section.list) { model in
                                    VStack{
                                        SearchItemCell(item: model)
                                    }.onAppear {
                                        self.listItemAppears(model)
                                    }
                                }
                            }
                        }
                    }
                }.navigationBarTitle(Text("Search"))
                    .searchable(text: $viewModel.searchKey, placement: SwiftUI.SearchFieldPlacement.automatic, prompt:  Text("请输入要搜索的关键词")){
                        // 历史搜索记录展示，方便用户直接选择常用关键字，提升用户体验, 这里最好放到用户的数据库中
                        if viewModel.state == .isStartInput {
                            ForEach(viewModel.historyKeys.reversed()) {[unowned viewModel] element in
                                Text(element).searchCompletion(element[...2]).onTapGesture {
                                    debugPrint("关键词 onTapGesture")
                                    viewModel.searchKey = element
                                    self.viewModel.search()
                                }
                            }
                        }
                    }.onChange(of: viewModel.searchKey) { newValue in
                        if !newValue.isEmpty {
                            // 当用户输入的停留时长超过1s后去触发网络请求
                            Debouncer.debounce(1) {
                                self.viewModel.search()
                            }
                        } else {
                            self.viewModel.clear()
                            pageIndex = 0
                        }
                    }
            } else {
                // Fallback on earlier versions
            }
        }
    }
}

struct SearchListView_Previews: PreviewProvider {
    static var previews: some View {
        SearchListView(viewModel: RepositoryListViewModel(mainScheduler: DispatchQueue.main))
    }
}

   

extension SearchListView {
    private func listItemAppears<Item: Identifiable>(_ item: Item) {
        if viewModel.repositorySection.isThresholdItem(offset: pageSize,
                                 item: item) {
            DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 3) {
                self.pageIndex += 1
                if self.pageIndex < 3 {
                    self.viewModel.searchNextPage()
                } else {
                    debugPrint("最后一页了")
                }
            }
        }
    }
    
}

