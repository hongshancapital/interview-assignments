//
//  ContentView.swift
//  hongshandemo
//
//  Created by 林纪涛 on 2023/4/10.
//

import SwiftUI

struct ContentView: View,EmptyViewDelegate {
    func retryAction(_ emptyView: EmptyView) {
        self.viewModel.refreshData()
    }
    
    @StateObject private var viewModel = AppListViewModel()
    
    fileprivate func modelListView() -> some View {
        let models = viewModel.dataSource
        
        return VStack {
            // 网络错误,或者没有数据时,显示错误页面
            if viewModel.errorState == .netWorkError ||
                (viewModel.errorState == .noData && models.count <= 0){
                EmptyView(delegate: self, errorState: viewModel.errorState)
            }else{
                if models.count > 0 {
                    ScrollView(showsIndicators: true) {
                        LazyVStack(alignment: .center, spacing: 15) {
                            if models.count > 0 {
                                // 列表
                                ForEach(models) { model in
                                    ListCell(model: model)
                                }
                                //底部加载试图
                                FootLoadingView(viewModel: self.viewModel).onAppear{
                                    if(viewModel.hasMoreData()){
                                        viewModel.loadMoreData()
                                    }
                                }
                            }
                        }.padding(EdgeInsets(top: 0, leading: 15, bottom: 0, trailing: 15))
                    }
                    .refreshable(action: {
                        viewModel.refreshData()
                    })
                    .background(Color(red: 244.0/255.0, green: 244.0/255, blue: 247.0/255))
                } else{
                    ActiveIndicationView(style: .large)
                        .frame(width: 50, height: 50)
                }
            }
        }
    }
    
    var body: some View {
        NavigationView {
            modelListView()
                .navigationBarTitle("APP", displayMode: .automatic)
        }.onAppear{
            UINavigationBar.appearance().titleTextAttributes = [.font:UIFont.systemFont(ofSize: 30, weight: UIFont.Weight.bold)]
        }
    }
}
struct ListCell : View {
    @ObservedObject var model: AppModel
    
    var body: some View{
        HStack{
            AsyncImage(url: URL(string: model.artworkUrl60 ?? "")) { image in
                image
            } placeholder: {
                ActiveIndicationView(style: .medium)
            }
            .frame(width: 60, height: 60)
            .cornerRadius(10)
            .overlay(
                RoundedRectangle(cornerRadius: 10, style: .continuous)
                    .stroke(Color(red: 227.0/255, green: 228.0/255, blue: 228.0/255), lineWidth: 0.5)
            )
            
            VStack(alignment: .leading) {
                Text(model.trackName ?? "")
                    .font(.headline)
                    .lineLimit(1)
                //.padding(EdgeInsets(top: 0, leading: 0, bottom: 5, trailing: 0))
                
                Spacer()
                //.frame(maxWidth: .infinity)
                Text(model.description ?? "")
                    .font(Font.system(size: 13))
                    .lineLimit(2)
                
                
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .leading)
            //.border(.pink)
            
            //Spacer()
            Button(action: {
                model.masked = !model.masked
            }, label: {
                Image(systemName: model.masked ? "heart.fill":"heart")
                    .resizable()
                    .foregroundColor(model.masked ? Color.red : Color.HSHeardColor)
                    .frame(width: 25,height: 25)
                
            }).frame(width: 30,height: 30)
        }
        .padding(10)
        .frame(height: 80)
        .background(Color.white)
        .cornerRadius(10)
    }
}

struct FootLoadingView: View{
    @ObservedObject var viewModel: AppListViewModel
    
    init(viewModel: AppListViewModel) {
        self.viewModel = viewModel
    }
    var body: some View{
        Group {
            HStack(alignment: .center) {
                if(viewModel.hasMoreData()){
                    HStack {
                        ActiveIndicationView(style: .medium)
                        Label("Loading", systemImage: "")
                            .frame(height: 50)
                            .background(Color.clear)
                    }
                }else{
                    Label("No More Data", systemImage: "")
                        .frame(height: 50)
                        .background(Color.clear)
                }
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
        
    }
}


