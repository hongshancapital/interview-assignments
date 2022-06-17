//
//  ContentView.swift
//  Homework
//
//  Created by miao jiafeng on 2022/6/12.
//

import SwiftUI

var length : Int = 10

struct ContentView: View {
    @State var realListData: [AppData] = getRealListData(length)
    @State var isloading: Bool = false
    @State var noMore: Bool = false

    var body: some View {
        
        NavigationView{
            List(){
                ForEach(0..<realListData.count - 1,id: \.self)
                {
                    index in
                    Section {
                        AppView(appData: realListData[index])
                            .frame(width: nil, height: 80)
                        }
                }
                //最后一个section
                Section(content: {
                    AppView(appData: realListData[realListData.count - 1])
                        .frame(width: nil, height: 80)
                        .onAppear {
                                length = length + 10
                                if(length <= listData.count){
                                    //可以请求数据
                                    isloading = true
                                    waitForSomeTime()
                                    realListData = getRealListData(length)
                                    isloading = false
                                }
                                else{
                                    //没有更多数据了
                                    length = listData.count
                                    realListData = getRealListData(length)
                                    noMore = true
                                }
                        }
                }, footer: {
                        if isloading{
                            //此处由于从本地加载数据太快，几乎看不到效果
                            //可以将此处的“isloading”换成“noMore”来观察效果
                            HStack(alignment: .center, spacing: 10, content: {
                                Spacer()
                                ProgressView()
                                    .progressViewStyle(.circular)
                                    .foregroundColor(.red)
                                
                                Text("Loading...")
                                    .font(.title3)
                                Spacer()
                            })
                        }
                        else if noMore{
                            Text("No more data.")
                                .font(.title3)
                                .frame(width: kScreenWidth, height: 30, alignment: .center)
                        }
                })
            }
            .onAppear{
                UITableView.appearance().sectionFooterHeight = 0
            }
            .navigationTitle("APP")
            .progressViewStyle(.circular)
            .refreshable {
                waitForSomeTime()
                length = 10
                realListData = getRealListData(length)
                noMore = false
                }
            }
        }
    }


struct ListFooter: View {
    var body: some View {
        Text("Remember to pack plenty of water and bring sunscreen.")
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
