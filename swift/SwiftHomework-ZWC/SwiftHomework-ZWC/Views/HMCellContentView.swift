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
    
    private struct ConstantValue {
        static let appiconViewImageSize: CGFloat = 50
        static let appiconViewCornerRadius: CGFloat = 5
        static let appiconViewProgressViewSize: CGFloat = 50
        static let textTrainingPadding: CGFloat = 50
    }
    
    @State var appStore: HMAppstore
    @Binding var app: HMApplication
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
            .padding(.trailing, ConstantValue.textTrainingPadding)
            
            HStack {
                Spacer()
                Button {
                    withAnimation {
                        appStore.collect(application: app)
                    }
                } label: {
                    Image(app.collectImageName)
                        .resizable()
                        .aspectRatio(1, contentMode: .fit)
                        .frame(width: app.collectImageSize.width, height: app.collectImageSize.height)
                    
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
                    .frame(width: ConstantValue.appiconViewImageSize, height: ConstantValue.appiconViewImageSize)
                    .cornerRadius(ConstantValue.appiconViewCornerRadius)
            case .success:
                
                Image(uiImage: applicationIconImage!)
                    .resizable()
                    .aspectRatio(1, contentMode: .fit)
                    .frame(width: ConstantValue.appiconViewImageSize, height: ConstantValue.appiconViewImageSize)
                    .cornerRadius(ConstantValue.appiconViewCornerRadius)
                
            case .loading:
                ProgressView().frame(width: ConstantValue.appiconViewProgressViewSize, height: ConstantValue.appiconViewProgressViewSize)
            }
        }.padding(.leading)
    }
    
}

