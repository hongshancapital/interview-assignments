//
//  ArtistListView.swift
//  Demo
//
//  Created by csmac05 on 2023/2/7.
//

import SwiftUI

extension View {
    @ViewBuilder func isHidden(_ isHidden: Bool) -> some View {
        if isHidden {
            self.hidden()
        } else {
            self
        }
    }
}

struct ArtistListView: View {
    @StateObject var viewModel = ArtistListViewModel()
    
    private var footerView: some View {
        HStack(alignment: .center) {
            Spacer()
            ProgressView().frame(width: 30, height: 30).isHidden(!viewModel.isLoadMore)
            Text(viewModel.isLoadMore ? "Loading..." : "No more data.")
                .foregroundColor(.gray)
            Spacer()
        }
        .listRowBackground(Color("backgroundColor"))
        .listRowSeparator(.hidden)
        .padding(.top, 12)
    }

    private var networkErrorView: some View {
        VStack {
            Spacer()
            Text("Network error, please retry...")
                .font(.headline)
                .foregroundColor(.gray)
                .padding()
            Button("Retry") {
                Task {
                    await viewModel.pullDown()
                }
            }
            .buttonStyle(.bordered)
            Spacer()
        }
    }

    private var emptyView: some View {
        VStack {
            Spacer()
            Text("No data, please retry...")
                .font(.headline)
                .foregroundColor(.gray)
                .padding()
            Button("Retry") {
                Task {
                    await viewModel.pullDown()
                }
            }
            .buttonStyle(.bordered)
            Spacer()
        }
    }
    
    private var listView: some View {
        List {
            ForEach(viewModel.list) { artist in
                ArtistListCell(artist: Binding(get: {
                    artist
                }, set: { _ in
                    Task {
                        await viewModel.likeArtist(artist)
                    }
                }))
                    .padding(12)
                    .listRowSeparator(.hidden)
                    .listRowBackground(Color("backgroundColor"))
                    .listRowInsets(EdgeInsets(top: 12, leading: 12, bottom: 0, trailing: 12))
                    .background(.white)
                    .cornerRadius(12)
            }
            footerView.task {
                await viewModel.pullUp()
            }
        }.listStyle(.grouped)
    }
    
    var body: some View {
        
        Group {
            if viewModel.isProgressing {
                ProgressView()
            } else {
                if viewModel.list.isEmpty {
                    if viewModel.error != nil {
                        networkErrorView
                    } else {
                        emptyView
                    }
                } else {
                    listView
                }
            }
        }.task {
            await viewModel.pullDown()
        }.refreshable {
            await viewModel.pullDown()
        }
        
    }
}


struct ArtistListView_Previews: PreviewProvider {
    
    static var previews: some View {
        ArtistListView()
    }
    
}
