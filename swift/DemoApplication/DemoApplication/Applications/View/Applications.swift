//
//  Applications.swift
//  DemoApplication
//
//  Created by aut_bai on 2022/6/25.
//

import SwiftUI

struct Applications: View {
    
    @ObservedObject var viewModel = ApplicationViewModel()
    
    var body: some View {
        if viewModel.status == .completed || viewModel.status == .loaded {
            NavigationView {
                List {
                    ForEach(viewModel.items) { item in
                        ApplicationRow(item: item)
                            .listRowBackground(EmptyView())
                            .listRowSeparator(.hidden)
                    }
                    
                    HStack{
                        Spacer()
                        if viewModel.status != .completed {
                            ProgressView()
                                .padding(.trailing, 5)
                        }
                        Text(viewModel.status == .completed ? "No more data" : "")
                            .foregroundColor(Color(uiColor: .placeholderText))
                            .onAppear(perform: viewModel.loadNextPage)
                        Spacer()
                    }
                    .listRowBackground(EmptyView())
                    .listRowSeparator(.hidden)
                }
                .navigationTitle("APP")
                .refreshable {
                    viewModel.refresh()
                }
                .listStyle(.plain)
                .background(Color(hex: 0xF5F5F5))
            }
            
        } else if (viewModel.status == .initializing) {
            ProgressView()
                .onAppear {
                    viewModel.refresh()
                }
        } else if (viewModel.status == .empty) {
            NavigationView {
                VStack {
                    Text("无数据，请点击重试")
                        .frame(width: 200, height: 200)
                        .offset(y: -60)
                        .onTapGesture {
                            viewModel.refresh()
                        }
                }
                .navigationTitle("APP")
            }
        }
    }
}

extension Color {
    init(hex: Int, alpha: Double = 1) {
            let components = (
                R: Double((hex >> 16) & 0xff) / 255,
                G: Double((hex >> 08) & 0xff) / 255,
                B: Double((hex >> 00) & 0xff) / 255
            )
            self.init(
                .sRGB,
                red: components.R,
                green: components.G,
                blue: components.B,
                opacity: alpha
            )
        }
}

struct Applications_Previews: PreviewProvider {
    static var previews: some View {
        Applications()
    }
}
