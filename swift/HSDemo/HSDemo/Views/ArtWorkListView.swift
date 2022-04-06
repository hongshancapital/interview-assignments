//
//  ArtWorkListView2.swift
//  SwitUI实战
//

//

import SwiftUI

struct ArtWorkListView: View {
    @ObservedObject var artworkFetcher: ArtWorkFetcher
    
    var body: some View {
        NavigationView {
            ScrollView(.vertical) {
                if artworkFetcher.artWorkList.count > 0 {
                    RefreshHeader(refreshing: $artworkFetcher.headerRefreshing, action: {
                        artworkFetcher.fetchArtWorks()
                    }) { _ in
                        if artworkFetcher.headerRefreshing {
                            ProgressView()
                        } else {
                            Text("下拉刷新")
                        }
                    }
                }
                ForEach(artworkFetcher.artWorkList) { artWork in
                    ArtWorkItemView(artworkFetcher: artworkFetcher, item: artWork)
                        .cornerRadius(15)
                        .padding(.horizontal, 15)
                }
                
                if artworkFetcher.artWorkList.count > 0 {
                    RefreshFooter(refreshing: $artworkFetcher.footerRefreshing, action: {
                        artworkFetcher.fetchMoreArtWorks()
                    }) {
                        if artworkFetcher.noMore {
                            Text("No more data !")
                                .foregroundColor(.secondary)
                                .padding()
                        } else {
                            ProgressView()
                                .padding()
                        }
                    }
                    .noMore(artworkFetcher.noMore)
                    .preload(offset: 50)
                }
            }
            .enableRefresh()
            .frame(width: UIScreen.main.bounds.width)
            .background(Color.black.opacity(0.06))
            
            .navigationBarTitle(Text("App"))
        }
    }
}

struct ArtWorkListView2_Previews: PreviewProvider {
    static var previews: some View {
        ArtWorkListView(artworkFetcher: ArtWorkFetcher())
    }
}
