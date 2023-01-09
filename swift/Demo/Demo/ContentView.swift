//
//  ContentView.swift
//  Demo
//
//  Created by lajsf on 2022/10/27.
//

import SwiftUI

struct ContentView: View {
    
    @ObservedObject var vm = ViewModel()
    @State var isLoadCompletion = false
    
    var body: some View {
        
        NavigationView{
            List {
                if isLoadCompletion {
                    ForEach(vm.data){ item in
                        Section {
                            HStack {
                                AsyncImage(url: URL.init(string: item.icon)) { img in
                                    img.resizable()
                                } placeholder: {
                                    ProgressView()
                                }
                                .frame(width: 68, height: 68)
                                .cornerRadius(16)
                                VStack {
                                    HStack {
                                        Text(item.title)
                                            .font(.system(size: 18))
                                            .lineLimit(1)
                                        Spacer()
                                    }
                                    HStack {
                                        Text(item.detail)
                                            .foregroundColor(.gray)
                                            .font(.system(size: 14))
                                            .lineLimit(2)
                                        Spacer()
                                    }
                                }
                                Spacer()
                                Button {
                                    withAnimation(.linear(duration: 0.25)) {
                                        vm.updateItem(item)
                                    }
                                } label: {
                                    Image.init(item.like ? "home_news_like" : "home_news_unlike")
                                        .resizable()
                                        .frame(width: 16, height: 16)
                                        .scaleEffect(item.like ? 1.2 : 1.0)
                                }
                                .buttonStyle(BorderlessButtonStyle())
                                
                            }
                        }.padding(EdgeInsets.init(top: 10, leading: 0, bottom: 10, trailing: 0))
                    }
                    HStack{
                        Spacer()
                        SimpleRefreshingView(nomore: vm.noMore)
                        Spacer()
                    }
                    .listRowBackground(Color.clear)
                    .onAppear {
                        if vm.isLoading || vm.noMore {
                            return
                        }
                        vm.loadMore()
                    }
                }else{
                    HStack{
                        Spacer()
                        ActivityIndicator(style: .medium)
                        Spacer()
                    }
                    .listRowBackground(Color.clear)
                    .padding(.top, 200)
                    .onAppear {
                        DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                            self.isLoadCompletion = true
                        }
                    }
                }
            }
            .onAppear{
                UITableView.appearance().sectionHeaderHeight = 6
                UITableView.appearance().sectionFooterHeight = 6
            }
            .refreshable {
                vm.refreshFetch()
            }
            .listStyle(InsetGroupedListStyle())
            .navigationTitle("App")
        }
    }
}


struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
