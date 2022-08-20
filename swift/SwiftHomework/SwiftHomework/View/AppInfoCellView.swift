//
//  AppInfoCellView.swift
//  SwiftHomework
//
//  Created by ljx on 2022/8/14.
//

import SwiftUI
import Combine
struct AppInfoCellView: View {
    @State var isLike:Bool = false
    var appDetailData:AppDetail!
    @State var cacheImage: UIImage?
    @State var cancellable = Set<AnyCancellable>()
    
    var body: some View {
        ZStack{
            HStack{
                Spacer().frame(width: 8)
                VStack{
                    HStack{
                        if cacheImage == nil {
                            ProgressView()
                                .frame(width: 60, height: 60, alignment: .center)
                                .cornerRadius(8)
                                .padding(EdgeInsets(top: 8, leading: 8, bottom: 8, trailing: 4))
                                .task {
                                    DispatchQueue.global().async {
                                        JXNetworkService.shared.loadImageWithUrl(urlString: appDetailData.artworkUrl60)
                                            .handleEvents(receiveSubscription: { _ in
                                                
                                            }, receiveOutput: { image in
                                                
                                            }, receiveCompletion: { error in
                                                print(error)
                                            }, receiveCancel: {
                                                print("cancel")
                                            })
                                            .sink { response in
                                                
                                            } receiveValue: { image in
                                                DispatchQueue.main.async {
                                                    cacheImage = image
                                                }
                                            }.store(in: &cancellable)
                                    }
                                }
                        }else{
                            Image(uiImage: cacheImage!)
                                .frame(width: 60, height: 60, alignment: .center)
                                .cornerRadius(8)
                                .padding(EdgeInsets(top: 8, leading: 8, bottom: 8, trailing: 4))
                                
                        }
                        
                        VStack(alignment: .leading) {
                            Text(appDetailData.trackName)
                                .bold()
                                .font(.system(size: 15, weight: Font.Weight.heavy, design: .default))
                                .lineLimit(1)
                            Text(appDetailData.resultDescription)
                                .font(.system(size: 12))
                                .lineLimit(2).frame(alignment: .topLeading)
                        }.frame(height:60, alignment:.topLeading)
                        Spacer()
                        Button(action: {
                            isLike = !isLike;
                        }, label: {
                            Image(isLike ? "heart-fill":"heart")
                                .resizable().aspectRatio(contentMode: .fit)
                                .frame(width: 20, height: 20, alignment: .center)
                        }).frame(width: 40, height: 40, alignment: .center)
                    }.background(.white)
                        .frame( height: 76, alignment: .center)
                        .cornerRadius(8)
                    Spacer().frame(height: 8)
                }
                Spacer().frame(width: 8)
            }
        }
    }
}

struct AppInfoCellView_Previews: PreviewProvider {
    static var previews: some View {
        AppInfoCellView()
    }
}
