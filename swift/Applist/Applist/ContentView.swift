//
//  ContentView.swift
//  Applist
//
//  Created by santcool on 2023/1/22.
//

import Kingfisher
import SwiftUI

@available(iOS 15.0, *)
struct ContentView: View {
    @StateObject var viewModel: AppListViewModel = AppListViewModel(page: 0)
    var body: some View {
        NavigationView {
            Group {
                if viewModel.firstLoad {
                    ProgressView().offset(y: -60)
                } else {
                    List {
                        ForEach(viewModel.resultData?.results ?? [], id: \.trackId) { (appModel: AppModel) in
                            let isLiked = viewModel.likedList.first(where: { $0.trackId == appModel.trackId }) != nil
                            HStack {
                                KFImage(URL(string: "\(appModel.artworkUrl100)"))
                                    .placeholder({
                                        ProgressView()
                                    })
                                    .resizable()
                                    .aspectRatio(contentMode: .fit)
                                    .cornerRadius(10)
                                    .frame(width: 51, height: 51, alignment: .center)
                                VStack(alignment: .leading) {
                                    Text("\(appModel.trackCensoredName)")
                                        .lineLimit(1)
                                        .font(.headline)
                                    Text("\(appModel.description)")
                                        .lineLimit(2)
                                        .font(/*@START_MENU_TOKEN@*/ .body/*@END_MENU_TOKEN@*/)
                                }

                                Spacer()
                                Button(action: {
                                    viewModel.toggleLikedData(appModel: appModel)
                                }, label: {
                                    Image(systemName: isLiked ? "heart.fill" : "heart")
                                        .foregroundColor(isLiked ? .red : .gray)
                                        .scaleEffect(isLiked ? 1.3 : 1)
                                        .frame(width: 44, height: 44)
                                        .animation(.easeInOut, value: isLiked)
                                })
                                .frame(width: 44, height: 44)
                                .buttonStyle(BorderlessButtonStyle())
                            }
                            .listRowSeparator(.hidden)
                            .listRowInsets(EdgeInsets(top: 15, leading: 15, bottom: 15, trailing: 0))
                            .listRowBackground(Color.clear)
                        }
                        if viewModel.resultData?.results.count ?? 0 > 0 {
                            LoadingMoreView(viewModel: viewModel)
                                .listRowSeparator(.hidden)
                        }
                    }
                    .listStyle(.plain)
                    .refreshable {
                        viewModel.refresh()
                    }
                    .navigationTitle("App")
                    .onTapGesture {
                    }
                }
            }
            .navigationViewStyle(.stack)
        }
        .onAppear {
            viewModel.refresh()
        }
        .alert(viewModel.loadError?.localizedDescription ?? "Network failed，please try later.", isPresented: .constant(viewModel.loadError != nil), actions: {})
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
