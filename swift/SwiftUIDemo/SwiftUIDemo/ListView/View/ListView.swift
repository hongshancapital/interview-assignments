//
//  ContentView.swift
//  SwiftUIDemo
//
//  Created by banban on 2022/5/16.
//

import SwiftUI
import UIKit

struct ListView: View {
    
    @ObservedObject var _viewModel = ListViewModel()
    
    var body: some View {
        NavigationView{
            VStack{
                if (_viewModel.isInitLoading) {
                    LoadingProgressView(isLarge: true)
                        .padding(.bottom,80)
                        .onAppear {
                            _viewModel.onRefresh()
                        }
                } else {
                    if (_viewModel.loadDataStatus == .failed) {
                        /// 数据加载失败
                        Spacer()
                        GrayText("数据加载失败，点击重试")
                            .padding(.bottom,80)
                            .onTapGesture {
                                _viewModel.retryLoadInitData()
                            }
                        Spacer()
                    } else {
                        /// 显示list列表
                        List {
                            ForEach(_viewModel.itemModels) { model in
                                ListItem(model: model) { model in
                                    _viewModel.onClickItemLikeButton(model)
                                }
                                .listRowBackground(Color.clear)
                                .listRowSeparator(.hidden)
                            }
                            /// FooterView
                            ListFooterView(status: _viewModel.loadDataStatus).onAppear {
                                _viewModel.loadMoreData()
                            }
                            .listRowBackground(Color.clear)
                            .listRowSeparator(.hidden)
                        }
                        .listStyle(.plain)
                        .refreshable {
                            _viewModel.onRefresh();
                        }
                        .progressViewStyle(CircularProgressViewStyle(tint: Color.red))
                    }
                }
            }
            .navigationBarTitle("App",displayMode: .large)
            .frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity)
            .background(Color.gray.opacity(0.3))
        }
        .navigationViewStyle(StackNavigationViewStyle())
    }
}

//MARK: - Preview
struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ListView()
    }
}


