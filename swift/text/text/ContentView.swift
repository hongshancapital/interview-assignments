//
//  ContentView.swift
//  text
//
//  Created by Harden.L on 2023/5/23.
//
import SwiftUI

struct ContentView: View {
    @State var isRefresh = false
    @State var isMore = false
    
    @ObservedObject  var viewModel = ArtistViewModel()
    
    var body: some View {
        NavigationView {
//            VStack(alignment: .leading, spacing: 5) {
                /*
                 offDown: 列表数据滑动总高
                 listH: 列表高度
                 refreshing: 下拉刷新加载UI的开关
                 isMore: 加载更多UI的开关
                 */
                RefreshScrollView(offDown: CGFloat(viewModel.artists.count) * 80, listH: ScreenH - kNavHeight - kBottomSafeHeight, refreshing: $isRefresh, isMore: $isMore) {
                    // 下拉刷新触发
                    DispatchQueue.main.asyncAfter(deadline: .now() + .seconds(3), execute: {
                        // 刷新完成，关闭刷新
                        self.loadData()
                        isRefresh = false
                    })
                } moreTrigger: {
                    // 上拉加载更多触发
                    DispatchQueue.main.asyncAfter(deadline: .now() + .seconds(3), execute: {
                        // 加载完成，关闭加载
                        //                    artists.append(contentsOf: viewModel.loadSource())
                        viewModel.loadMoreData(index: viewModel.artists.count)
                        isMore = false
                    })
                } content: {
                    VStack(alignment: .leading, spacing: 10){
                        ForEach(viewModel.artists) { art in
                            TableRow(art: art,favor: { art in
                                viewModel.changeList(art)
                            }).background(Color.white).cornerRadius(10).frame(width: ScreenW,alignment: .center)
                        }
                    }
                    //                List{
                    //                    VStack(alignment: .leading, spacing: 10){
                    //                        ForEach(viewModel.artists) { art in
                    //                            TableRow(art: art,favor: { art in
                    //                                viewModel.changeList(art)
                    //                            }).background(Color.white).cornerRadius(10).frame(width: ScreenW,alignment: .center)
                    //                        }
//                                        }.listRowBackground(Color(red: 0.9, green: 0.9, blue: 0.9))
//                                                                  .padding(.trailing, 5)
//                                                                  .padding(.leading, 5)
//                                                                  .padding(.top, 2)
//                                                                  .padding(.bottom, 2)
                    //                }.frame(width: ScreenW, height: ScreenH - kNavHeight - kBottomSafeHeight).listStyle(.plain).background(.clear)
                }.background(Color.clear).listStyle(.plain)
                .onAppear(){
                    self.loadData()
                }
                //            Spacer()
//            }
//            .padding()
            .background(Color(red: 0.9, green: 0.9, blue: 0.9))
            .listStyle(GroupedListStyle())
            .navigationBarTitle(Text("Example"), displayMode: .large)
        }
    }
    
    func loadData(){
        viewModel.clear()
        viewModel.loadSource()
    }
}



struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
