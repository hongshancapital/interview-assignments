//
//  HomeView.swift
//  SocialAppList
//
//  Created by 刘飞 on 2023/4/29.
//

import SwiftUI
import Introspect

struct HomeView: View {
    @StateObject var vm = ListViewModel()
    @State var load: Bool = false
    
    var body: some View {
        NavigationView {
            Group {
                if vm.showEmptyView {
                    PlaceholderView {
                        VStack {
                            Button("获取数据失败,点击重新获取") {
                                vm.showEmptyView = false
                                vm.send(.refresh(isFirst: true))
                            }
                            .frame(height: 50)
                            .padding(.horizontal, 10)
                            .border(Color.blue, cornerRadius: 6, width: 0.5)
                        }
                    }

                } else {
                    if vm.list.isEmpty {
                        ProgressView()
                        
                    } else {
                        ZStack {
                            Color("color_1").ignoresSafeArea()
                            
                            VStack(spacing: 10) {
                                ForEach($vm.list) { model in
                                    AppCell(model: model)
                                }
                            }
                            .padding([.horizontal, .top], 10)
                            .footerRefresh(hasMore: $vm.hasMore, {
                                vm.send(.refresh(isFirst: false))
                            })
                            .headerRefresh(refreshing: $vm.refreshing, {
                                vm.send(.refresh(isFirst: true))
                            })
                        }
                        .navigationBarTitle("App", displayMode: .large)
                        .environmentObject(vm)
                    }
                }
            }
            .onAppear {
                if load { return }
                load = true
                vm.send(.refresh(isFirst: true))
            }
        }
        .navigationViewStyle(.stack)
    }
}
 
