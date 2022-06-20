//
//  AppRow.swift
//   interviewiOS
//
//  Created by aa on 2022/5/4
//

import SwiftUI

struct AppRow: View {
    var appInfo: AppInfo
    let placeholderOne = UIImage(named: "60x60bb.jpg")
    
    @State private var remoteImage : UIImage? = nil
    @State private var imageColor: Color = .gray
    @State var imageWaiting = true
    var body: some View {
        VStack {
            HStack {
                Spacer().frame(width:10)
                Image(uiImage: self.remoteImage ?? placeholderOne!)
                    .border(Color.init(UIColor(red:0.902, green:0.902, blue:0.902, alpha:1.000)), width: 2)
                    .cornerRadius(6)
                    .onAppear(perform: fetchRemoteImage)
                if imageWaiting {
                    ProgressView()
                }
                VStack(alignment: .leading) {
                    Text(verbatim: appInfo.trackName)
                        .font(Font.custom("PingFangSC-Regular", fixedSize: 18))
                        .bold()
                    Spacer()
                    Text(verbatim: appInfo.description)
                        .font(Font.custom("PingFangSC-Regular", fixedSize: 16))
                        .lineLimit(2)
                }
                Spacer()
                
                Image(systemName: "heart")
                    .imageScale(.medium)
                    .foregroundColor(imageColor)
                    .gesture(
                        TapGesture()
                            .onEnded { _ in
                                imageColor = imageColor == .red ? .gray : . red
                            }
                    )
                Spacer().frame(width:10)
            }
            .frame(height:90)
            .cornerRadius(12)
            .background(Color.white)
        }
        .listRowInsets(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 0))
        .frame(height: 100)
        .listRowBackground(Color.clear)
        .listRowSeparator(.hidden)
    }
    
    func fetchRemoteImage()
    {
        guard let url = URL(string: self.appInfo.artworkUrl60) else { return }
        URLSession.shared.dataTask(with: url){ (data, response, error) in
            if let image = UIImage(data: data!){
                self.imageWaiting = false
                self.remoteImage = image
            }
            else{
                print(error ?? "")
            }
        }.resume()
    }
}

struct AppRow_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            AppRow(appInfo: APPListInfo[0])
            AppRow(appInfo: APPListInfo[1])
        }
        .previewLayout(.fixed(width: 300, height: 70))
    }
}

