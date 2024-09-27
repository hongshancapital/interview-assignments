//
//  EmojiMemoryGameView.swift
//  Memorize
//
//  Created by 费九林 on 2022/9/26.
//

import SwiftUI


struct ItemListView: View {
    
    @ObservedObject  var viewModel: ItemMemoryGame
    @State private var headerRefreshing: Bool = false
    @State private var footerRefreshing: Bool = false
    @State private var noMore: Bool = false
   
    let backRed = UIColor(displayP3Red: 245/255, green: 245/255, blue: 245/255, alpha: 1.0)

    var body: some View {
        
        GeometryReader { geometry in
            
        NavigationView{
            
            ScrollView {
                
                if viewModel.cards.count > 0 {
                    RefreshHeader(refreshing: $headerRefreshing, action:  {
                        self.reload()
                       
                    }) { progress in
                        
                        if self.headerRefreshing {
                            RefreshingView()
                            
                        } else {
                            PullToRefreshView(progress: progress)
                        }
                        
                    }

                    
                }
                
                ForEach(viewModel.cards) { card in
                   
                    CardView(caid: card,viewModel:viewModel)
                        .padding(EdgeInsets(top: 10, leading: 10, bottom: -5, trailing: 10))
                }
                
                if viewModel.cards.count  > 0 {
                    RefreshFooter(refreshing: $footerRefreshing, action: {
                        self.loadMore()
                    }) {
                        if self.noMore {
                            Text("No more data")
                                .foregroundColor(.secondary)
                                .padding()
                        } else {
                                RefreshingView()
                                .padding()
                        }
                    }
                    .noMore(noMore)
                    .preload(offset: 50)
                }

            }
            
            
            .enableRefresh()
            .overlay(Group {
                if viewModel.cards.count == 0 {
                    ActivityIndicator(style: .medium)
                } else {
                    EmptyView()
                }
            })
            .onAppear { self.reload() }
            
            .listStyle(.plain)
            .navigationTitle(Text("App"))
            .background(Color(backRed))

        }
        
      }
        
    }
    
    
    func reload() {
        
        viewModel.generateItems(refreshing: true) {
            self.headerRefreshing = false
            self.noMore = false
        }
           
    }
    
    func loadMore() {
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            viewModel.generateItems(refreshing: false) {
            self.footerRefreshing = false
            self.noMore = viewModel.cards.count > 50
        }
      }
    }
    
}


struct CardView: View {
    
    var caid :MemoryGame<String>.Card
    var viewModel: ItemMemoryGame
    
    var body: some View{
        
        ZStack (alignment: .leading) {
            
            RoundedRectangle(cornerRadius: 10).fill(Color.white)
            HStack{
                AsyncImage(url: URL(string: caid.imageUrl))
                    .scaledToFill()
                    .frame(width: 60, height: 60,alignment: .leading)
                    .cornerRadius(5)
                    .padding(.horizontal,4)
                    .padding(EdgeInsets(top: 10, leading: 10, bottom: 10, trailing: 0))

                VStack(alignment: .leading, spacing: 5) {
                    Text(caid.title)
                        .font(.system(size: 16))
                        .fontWeight(.semibold)
                        .lineLimit(1)
                        .padding(.trailing,40)

                    Text(caid.count)
                        .font(.system(size: 14))
                        .fontWeight(.semibold)
                        .lineLimit(2)
                        .padding(.trailing,40)
                   
                }
                             
            }
            
            HStack{
                Button{
                    viewModel.chooss(card: caid)
                } label:{
                    Image(caid.isLike ? "icon_give_selected": "icon_give_normal")
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .trailing)
                .padding(.trailing,10)
              
            }
            
        }
    
    }
    
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ItemListView(viewModel: ItemMemoryGame())
    }
}

