//
//  LHListView.swift
//  LHAppListDemo
//
//  Created by lzh on 2022/3/26.
//

import SwiftUI

struct LHListViewCellHeadIconIV : View {
    let headImgUrl : String
    var body: some View {
        ZStack {
            AsyncImage(url: URL(string: headImgUrl)) { image in
                image
                    .resizable()
                    .scaledToFill()
                
            } placeholder: {
                ProgressView()
                    .frame(width: 56, height: 56)
            }
            .frame(width: 56, height: 56)
            .cornerRadius(8)
        }
    }
}

struct LHListViewRefreshFooter : View{
    
    var loadState: LHViewModelLoadDataState
        
    @Environment(\.safeAreaInsets) private var safeAreaInsets

    var body: some View {
        if loadState == .noData {
            HStack {
                Spacer()
                Text("No more data.")
                Spacer()
            }
            .padding(EdgeInsets(top: 0, leading: 0, bottom: safeAreaInsets.bottom, trailing: 0))
            .listRowBackground(Color.clear)
        }else if loadState == .loading {
            HStack(alignment: .center, spacing: 1) {
                Spacer()
                ProgressView()
                    .frame(width: 36, height: 36)
                Text("Loading...")
                Spacer()
            }
            .listRowBackground(Color(hex: 0xF4F3F7))
            .padding(EdgeInsets(top: 0, leading: 0, bottom: safeAreaInsets.bottom, trailing: 0))
        }else if loadState == .failed {
            
            HStack(alignment: .center, spacing: 1) {
                Spacer()
                Text("Load failed")
                Spacer()
            }
            .listRowBackground(Color.clear)
            .padding(EdgeInsets(top: 0, leading: 0, bottom: safeAreaInsets.bottom, trailing: 0))
        }else {
            HStack (alignment: .center, spacing: 1) {
                Spacer()
                Text("Pull to load data")
                Spacer()
            }
            .listRowBackground(Color.clear)
            .padding(EdgeInsets(top: 0, leading: 0, bottom: safeAreaInsets.bottom, trailing: 0))
        }
    }
}


//struct ContentView_Previews1: PreviewProvider {
//    static var previews: some View {
//        Group {
//            LHListViewRefreshFooter()
//        }
//    }
//}

struct LHListViewCell : View {
    
    let appInfo: LHAppInfo
    
    var likeAction : ((LHAppInfo)->())?
    
    @State private var scale = 1.0
    
    var body: some View {
        HStack {
            LHListViewCellHeadIconIV(headImgUrl: appInfo.imgUrl)
                .padding(EdgeInsets(top: 10, leading: 10, bottom: 10, trailing: 6))
            
            VStack(alignment: .leading, spacing: 6) {
                Text(appInfo.name).font(Font.system(size: 17, weight: .bold))
                    .lineLimit(2)
                Text(appInfo.content).font(Font.system(size: 13))
                    .lineLimit(2)
            }
            Spacer()
            Image(systemName: appInfo.isLike == true ? "heart.fill" : "heart")
                .resizable()
                .scaledToFit()
                .frame(width: appInfo.isLike == true ? 24 : 20)
                .foregroundColor( appInfo.isLike == true ? Color.red : Color.gray )
                .padding(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: appInfo.isLike == true ? 16 : 18))
                .onTapGesture {
                    withAnimation {
                        scale = 1.1
                        likeAction?(appInfo)
                    }
                    
                }
                .scaleEffect(x: scale, y: scale, anchor: .center)
        }
        .background(Color.white)
        .listRowBackground(Color.clear)
        .listRowSeparator(.hidden)
        .cornerRadius(10)
        
    }
}


struct LHListView : View {
    
    @ObservedObject var viewModel : LHViewModel
    
    var body: some View {
        
        List(viewModel.models){ model in
            LHListViewCell(appInfo: model,likeAction: { info in
                viewModel.like(app: info)
                
            })
                .background(Color.clear)
            if model.id == viewModel.models.last!.id {
                footer
            }
        }
        .listStyle(.plain)
        .modifier(LHNavModifier())
        .background(Color(hex: 0xF4F3F7))
        .refreshable {
            viewModel.loadRecentAppInfo()
        }
    }
    
    
    
    var footer : some View {
        LHListViewRefreshFooter(loadState: viewModel.loadState)
            .background(Color.clear)
            .onAppear {
                viewModel.loadMoreAppInfo()
            }
    }
}

