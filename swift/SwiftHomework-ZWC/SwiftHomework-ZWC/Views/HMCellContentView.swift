//
//  HMCellContentView.swift
//  SwiftHomework-ZWC
//
//  Created by 油菜花 on 2022/2/16.
//

import SwiftUI

struct CellContentView: View {
    
    enum AppIconStatus {
        case failure
        case success
        case loading
    }
    
    @Binding var app: HMApplication
    @State var collectIconSize: CGFloat = 30
    @State var applicationIconImage: UIImage?
    @State var appIconStates: AppIconStatus = .failure
    
    var body: some View {
        
        ZStack {
            HStack {
                appiconView.onAppear {
                    fetchImageData(withImageUrl: app.artworkUrl512)
                }
                VStack(alignment: .leading) {
                    Text(app.trackName)
                        .font(.system(size: 15, weight: .bold))
                        .frame(alignment: .leading)
                        .lineLimit(1)
                    Text(app.description)
                        .font(.system(size: 12))
                        .lineLimit(2)
                        .multilineTextAlignment(.leading)
                }
                .padding([.top, .bottom])
                Spacer()
            }
            .padding(.trailing, 50)
            
            HStack {
                Spacer()
                Button {
                    withAnimation {
                        app.isCollected = !app.isCollected
                        app.collectImageName = app.isCollected ? "icon002" : "icon001"
                        collectIconSize = app.isCollected ? 40 : 30
                    }
                } label: {
                    Image(app.collectImageName)
                        .resizable()
                        .aspectRatio(1, contentMode: .fit)
                        .frame(width: collectIconSize, height: collectIconSize)
                    
                }
                .padding(.trailing, 15)
            }
        }
        .background(Color.white)
        .clipped()
        .cornerRadius(8)
    }
    
    private func fetchImageData(withImageUrl url: String) {
        guard let imageUrl = URL(string: url) else {
            return
        }
        self.appIconStates = .loading
        let task = URLSession.shared.dataTask(with: imageUrl) { imageData, response, error in
            guard let imageData = imageData else {
                self.appIconStates = .failure
                return
            }
            guard let image = UIImage(data: imageData) else {
                self.appIconStates = .failure
                return
            }
            self.applicationIconImage = image
            self.appIconStates = .success
        }
        DispatchQueue.main.asyncAfter(deadline: DispatchTime.now()+1) {
            task.resume()
        }
    }
}

// MARK: - UI Widget
extension CellContentView {
    
    var appiconView: some View {
        Group {
            switch appIconStates {
            case .failure:
                
                Image("icon003")
                    .resizable()
                    .aspectRatio(1, contentMode: .fit)
                    .frame(width: 50, height: 50)
                    .cornerRadius(5)
            case .success:
                
                Image(uiImage: applicationIconImage!)
                    .resizable()
                    .aspectRatio(1, contentMode: .fit)
                    .frame(width: 50, height: 50)
                    .cornerRadius(5)
                
            case .loading:
                ProgressView().frame(width: 50, height: 50)
            }
        }.padding(.leading)
    }
    
}

