//
//  RefreshScrollView.swift
//  SwiftHomework
//
//  Created by ljx on 2022/8/17.
//
import SwiftUI

private enum PositionType {
  case fixed, moving
}

private struct Position: Equatable {
  let type: PositionType
  let y: CGFloat
}

private struct PositionPreferenceKey: PreferenceKey {
  typealias Value = [Position]

  static var defaultValue = [Position]()

  static func reduce(value: inout [Position], nextValue: () -> [Position]) {
    value.append(contentsOf: nextValue())
  }
}

private struct PositionIndicator: View {
  let type: PositionType

  var body: some View {
    GeometryReader { proxy in
        Color.clear
          .preference(key: PositionPreferenceKey.self, value: [Position(type: type, y: proxy.frame(in: .global).minY)])
     }
  }
}

public typealias RefreshComplete = () -> Void

public typealias OnRefresh = (@escaping RefreshComplete) -> Void

public let defaultRefreshThreshold: CGFloat = 130


public enum RefreshState {
  case waiting, topPrimed, topLoading,bottomPrimed,bottomLoading
}

public typealias RefreshProgressBuilder<Progress: View> = (RefreshState) -> Progress
public typealias LoadMoreProgressBuilder<MoreProgress: View> = (RefreshState) -> MoreProgress

public let defaultLoadingViewBackgroundColor = Color(UIColor.clear)

public struct RefreshableScrollView<Progress,MoreProgress, Content>: View where Progress: View,MoreProgress: View, Content: View {
  let showsIndicators: Bool
  let loadingViewBackgroundColor: Color
  let threshold: CGFloat
  let onRefresh: OnRefresh
  let onLoadMore:OnRefresh
  let refreshProgress: RefreshProgressBuilder<Progress>
  let loadMoreProgress: LoadMoreProgressBuilder<MoreProgress>
  let content: () -> Content
  @State private var offset: CGFloat = 0
  @State private var state = RefreshState.waiting 
    @State private var topOpacity:CGFloat = 0
    @State private var bottomOpacity:CGFloat = 0
    @State private var bottomY: CGFloat = 0
    let pullReleasedFeedbackGenerator = UIImpactFeedbackGenerator(style: .light)

  public init(showsIndicators: Bool = true,
              loadingViewBackgroundColor: Color = defaultLoadingViewBackgroundColor,
              threshold: CGFloat = defaultRefreshThreshold,
              onRefresh: @escaping OnRefresh,
              onLoadMore: @escaping OnRefresh,
              @ViewBuilder refreshProgress: @escaping RefreshProgressBuilder<Progress>,
              @ViewBuilder loadMoreProgress: @escaping LoadMoreProgressBuilder<MoreProgress>,
              @ViewBuilder content: @escaping () -> Content) {
    self.showsIndicators = showsIndicators
    self.loadingViewBackgroundColor = loadingViewBackgroundColor
    self.threshold = threshold
    self.onRefresh = onRefresh
    self.onLoadMore = onLoadMore
    self.refreshProgress = refreshProgress
    self.loadMoreProgress = loadMoreProgress
    self.content = content
  }

  public var body: some View {
    ScrollView(showsIndicators: showsIndicators) {
        
      ZStack(alignment: .top) {
        PositionIndicator(type: .moving)
          .frame(height: 0)

          content()
           .background(GeometryReader { geometry -> Color in
                if(geometry.size.height != bottomY){
                    DispatchQueue.main.async {
                        bottomY = geometry.size.height
                    }
                }
                return Color.clear
            })

          ///下拉刷新
          ZStack {
            Rectangle()
              .foregroundColor(loadingViewBackgroundColor)
              .frame(height: threshold)
              refreshProgress(state)
          }.offset(y: -threshold).opacity(state == .topLoading ? 1 : topOpacity)
          
          //加载更多
          ZStack {
            Rectangle()
              .foregroundColor(loadingViewBackgroundColor)
              .frame(height: threshold - 20)
              loadMoreProgress(state)
          }.offset(y: bottomY).opacity(state == .bottomLoading ? 1 :bottomOpacity)
        }
      }

      .background(PositionIndicator(type: .fixed))

      .onPreferenceChange(PositionPreferenceKey.self) { values in
        let movingY = values.first { $0.type == .moving }?.y ?? 0
        let fixedY = values.first { $0.type == .fixed }?.y ?? 0
        let bottomH = threshold - 20
        offset = movingY - fixedY
          topOpacity = offset/threshold
          bottomOpacity = -offset/bottomH
        if state != .topLoading {
          DispatchQueue.main.async {
            if offset > threshold && state == .waiting {
              state = .topPrimed
            }
              else if -offset > bottomH && state == .waiting {
                  state = .bottomPrimed
            }
              else if offset < threshold && state == .topPrimed {
                  state = .topLoading
                  self.pullReleasedFeedbackGenerator.impactOccurred()
                  onRefresh {
                      withAnimation {
                          self.state = .waiting
                      }
                  }
            }
              else if -offset < bottomH && state == .bottomPrimed {
                  state = .bottomLoading
                  self.pullReleasedFeedbackGenerator.impactOccurred()
                  onLoadMore {
                      withAnimation {
                          self.state = .waiting
                      }
                  }
              }
          }
        }
      }
    }
    
}

public extension RefreshableScrollView where Progress == RefreshActivityIndicator,MoreProgress == RefreshActivityIndicator {
    init(showsIndicators: Bool = true,
         loadingViewBackgroundColor: Color = defaultLoadingViewBackgroundColor,
         threshold: CGFloat = defaultRefreshThreshold,
         onRefresh: @escaping OnRefresh,
         onLoadMore: @escaping OnRefresh,
         @ViewBuilder content: @escaping () -> Content) {
        self.init(showsIndicators: showsIndicators,
                  loadingViewBackgroundColor: loadingViewBackgroundColor,
                  threshold: threshold,
                  onRefresh: onRefresh,
                  onLoadMore: onLoadMore,
                  refreshProgress: { state in
                    RefreshActivityIndicator(isAnimating: state == .topLoading) {
                        $0.hidesWhenStopped = false
                    }
        }, loadMoreProgress: {state in
            RefreshActivityIndicator(isAnimating: state == .topLoading) {
                $0.hidesWhenStopped = false
            }
        },
                 content: content)
    }
}

public struct RefreshActivityIndicator: UIViewRepresentable {
  public typealias UIView = UIActivityIndicatorView
  public var isAnimating: Bool = true
  public var configuration = { (indicator: UIView) in }

  public init(isAnimating: Bool, configuration: ((UIView) -> Void)? = nil) {
    self.isAnimating = isAnimating
    if let configuration = configuration {
      self.configuration = configuration
    }
  }

  public func makeUIView(context: UIViewRepresentableContext<Self>) -> UIView {
    UIView()
  }

  public func updateUIView(_ uiView: UIView, context: UIViewRepresentableContext<Self>) {
    isAnimating ? uiView.startAnimating() : uiView.stopAnimating()
    configuration(uiView)
  }
}

#if compiler(>=5.5)
@available(iOS 15.0, *)
public extension RefreshableScrollView {
    init(showsIndicators: Bool = true,
         loadingViewBackgroundColor: Color = defaultLoadingViewBackgroundColor,
         threshold: CGFloat = defaultRefreshThreshold,
         action: @escaping @Sendable () async -> Void,
         @ViewBuilder refreshProgress: @escaping RefreshProgressBuilder<Progress>,
         @ViewBuilder loadMoreProgress: @escaping LoadMoreProgressBuilder<MoreProgress>,
         @ViewBuilder content: @escaping () -> Content) {
        self.init(showsIndicators: showsIndicators,
                  loadingViewBackgroundColor: loadingViewBackgroundColor,
                  threshold: threshold,
                  onRefresh: { refreshComplete in
                    Task {
                        await action()
                        refreshComplete()
                    }
                },
                  onLoadMore: { loadMoreComplete in
                    Task {
                        await action()
                        loadMoreComplete()
                    }
        },
                  refreshProgress: refreshProgress,
                  loadMoreProgress: loadMoreProgress,
                  content: content)
    }
}
#endif

public struct RefreshableCompat<Progress>: ViewModifier where Progress: View {
    private let showsIndicators: Bool
    private let loadingViewBackgroundColor: Color
    private let threshold: CGFloat
    private let onRefresh: OnRefresh
    private let onLoadMore: OnRefresh
    private let refreshProgress: RefreshProgressBuilder<Progress>
    private let loadMoreProgress: RefreshProgressBuilder<Progress>
    public init(showsIndicators: Bool = true,
                loadingViewBackgroundColor: Color = defaultLoadingViewBackgroundColor,
                threshold: CGFloat = defaultRefreshThreshold,
                onRefresh: @escaping OnRefresh,
                onLoadMore: @escaping OnRefresh,
                @ViewBuilder refreshProgress: @escaping RefreshProgressBuilder<Progress>,
                @ViewBuilder loadMoreProgress: @escaping RefreshProgressBuilder<Progress>) {
        self.showsIndicators = showsIndicators
        self.loadingViewBackgroundColor = loadingViewBackgroundColor
        self.threshold = threshold
        self.onRefresh = onRefresh
        self.onLoadMore = onLoadMore
        self.refreshProgress = refreshProgress
        self.loadMoreProgress = loadMoreProgress
    }
    
    public func body(content: Content) -> some View {
        RefreshableScrollView(showsIndicators: showsIndicators,
                              loadingViewBackgroundColor: loadingViewBackgroundColor,
                              threshold: threshold,
                              onRefresh: onRefresh,
                              onLoadMore: onLoadMore,
                              refreshProgress: refreshProgress,
                              loadMoreProgress:loadMoreProgress) {
            content
        }
    }
}

#if compiler(>=5.5)
@available(iOS 15.0, *)
public extension List {
    @ViewBuilder func refreshableCompat<Progress: View>(showsIndicators: Bool = true,
                                                        loadingViewBackgroundColor: Color = defaultLoadingViewBackgroundColor,
                                                        threshold: CGFloat = defaultRefreshThreshold,
                                                        onRefresh: @escaping OnRefresh,
                                                        onLoadMore: @escaping OnRefresh,
                                                        @ViewBuilder refreshProgress: @escaping RefreshProgressBuilder<Progress>,
                                                        @ViewBuilder loadMoreProgress: @escaping RefreshProgressBuilder<Progress>) -> some View {
        if #available(iOS 15.0, macOS 12.0, *) {
            self.refreshable {
                await withCheckedContinuation { cont in
                    onRefresh {
                        cont.resume()
                    }
                }
            }
        } else {
            self.modifier(RefreshableCompat(showsIndicators: showsIndicators,
                                            loadingViewBackgroundColor: loadingViewBackgroundColor,
                                            threshold: threshold,
                                            onRefresh: onRefresh,
                                            onLoadMore: onLoadMore,
                                            refreshProgress: refreshProgress,
                                           loadMoreProgress: loadMoreProgress))
        }
    }
}
#endif

public extension View {
    @ViewBuilder func refreshableCompat<Progress: View>(showsIndicators: Bool = true,
                                                        loadingViewBackgroundColor: Color = defaultLoadingViewBackgroundColor,
                                                        threshold: CGFloat = defaultRefreshThreshold,
                                                        onRefresh: @escaping OnRefresh,
                                                        onLoadMore: @escaping OnRefresh,
                                                        @ViewBuilder refreshProgress: @escaping RefreshProgressBuilder<Progress>,
                                                        @ViewBuilder loadMoreProgress: @escaping RefreshProgressBuilder<Progress>) -> some View {
        self.modifier(RefreshableCompat(showsIndicators: showsIndicators,
                                        loadingViewBackgroundColor: loadingViewBackgroundColor,
                                        threshold: threshold,
                                        onRefresh: onRefresh,
                                        onLoadMore: onRefresh,
                                        refreshProgress: refreshProgress,
                                        loadMoreProgress: loadMoreProgress))
    }
}




